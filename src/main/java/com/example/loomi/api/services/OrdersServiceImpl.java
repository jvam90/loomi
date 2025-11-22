package com.example.loomi.api.services;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.loomi.api.Validation.ProductValidatorFactory;
import com.example.loomi.api.Validation.ProductValidationException;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.infrastructure.JPAEntities.OrderEntity;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;
import com.example.loomi.infrastructure.JPAEntities.ProductEntity;
import com.example.loomi.infrastructure.Repositories.CustomerRepository;
import com.example.loomi.infrastructure.Repositories.OrderRepository;
import com.example.loomi.infrastructure.Repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductValidatorFactory productValidatorFactory;

    @Value("${orders.max.attempts}")
    private int maxAttempts;

    private static final Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    public OrdersServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository,
            ProductRepository productRepository, ProductValidatorFactory productValidatorFactory) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productValidatorFactory = productValidatorFactory;
    }

    @Override
    public List<OrderEntity> getAllOrdersByCustomerId(String customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }

        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Optional<OrderEntity> findByIdAndCustomerId(String orderId, String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }

        var customerOrder = orderRepository.findByOrderIdAndCustomerId(orderId, customerId);

        if (customerOrder.isEmpty()) {
            throw new IllegalArgumentException(
                    "Order with ID " + orderId + " for Customer ID " + customerId + " does not exist.");
        }

        return customerOrder;
    }

    // Método utiliza trava otimista para tentar criar o pedido e evitar
    // concorrência
    @Override
    @Transactional
    public OrderEntity createOrder(OrderEntity orderEntity) {
        log.info("createOrder called for customerId={}", orderEntity.getCustomerId());
        int attempts = 0;
        while (true) {
            try {
                return doCreateOrderTransactional(orderEntity);
            } catch (OptimisticLockingFailureException ex) {
                attempts++;
                log.warn("Optimistic lock detected when creating order for customer {} (attempt {})",
                        orderEntity.getCustomerId(), attempts);
                if (attempts >= maxAttempts) {
                    log.error("Exceeded max retry attempts ({}). Failing order creation for customer {}", maxAttempts,
                            orderEntity.getCustomerId());
                    throw new ProductValidationException(
                            "Could not reserve stock due to concurrent updates. Please try again.");
                }

                // Aguarda um tempo antes de tentar novamente
                try {
                    Thread.sleep(50L * attempts);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.error("Interrupted while retrying order creation", ie);
                    throw new ProductValidationException("Interrupted while retrying order creation", ie);
                }
            }
        }
    }

    private OrderEntity doCreateOrderTransactional(OrderEntity orderEntity) {
        if (!customerRepository.existsById(orderEntity.getCustomerId())) {
            throw new IllegalArgumentException("Customer with ID " + orderEntity.getCustomerId() + " does not exist.");
        }

        if (orderEntity.getOrderId() != null && !orderEntity.getOrderId().isEmpty()) {
            throw new IllegalArgumentException("Order ID must be null or empty when creating a new order.");
        }

        if (orderEntity.getItems() == null || orderEntity.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        // Verifica se o pedido é totalmente corporativo
        checkIfOrderIsFullyCorporate(orderEntity);

        orderEntity.setTotal(new BigDecimal(0));
        log.debug("Running validators for {} items",
                orderEntity.getItems() == null ? 0 : orderEntity.getItems().size());
        // validate each item using the registered validators
        for (OrderItemEntity orderItemEntity : orderEntity.getItems()) {

            String requestedProductId = orderItemEntity.getProduct().getProductId();
            int orderRequestedAmount = orderItemEntity.getQuantity() == null ? 0 : orderItemEntity.getQuantity();
            var productEntity = productRepository.findById(requestedProductId);

            if (productEntity.isEmpty()) {
                log.warn("Product with ID {} does not exist", requestedProductId);
                throw new IllegalArgumentException(
                        "Product with ID " + requestedProductId + " does not exist.");
            }

            if (orderItemEntity.getQuantity() == null || orderItemEntity.getQuantity() <= 0) {
                throw new IllegalArgumentException(
                        "Invalid quantity for product ID: " + orderItemEntity.getProduct().getProductId());
            }

            orderItemEntity.setProduct(productEntity.get());
            productValidatorFactory.get(orderItemEntity.getProduct().getProductType()).validate(orderItemEntity);
            log.debug("Validated item productId={} qty={}", requestedProductId, orderItemEntity.getQuantity());

            // Método para atualizar o estoque no banco de acordo com o tipo do produto,
            // pois pode vir de diferentes colunas.
            updateProductStockAccordingToProductType(orderItemEntity, requestedProductId, orderRequestedAmount,
                    productEntity);

            // Se o produto for digital, mock de envio de email com a chave de ativação
            checkIfDigitalProductAndSendEmail(orderEntity, orderItemEntity, productEntity);

            // se produto físico, mockar a data de entrega:
            if (productEntity.get().getProductType().equals(ProductType.PHYSICAL)) {
                orderItemEntity.setDeliveryTime("10 days");
            }

            // Se produto for assinatura, mockar data de primeira cobrança para daqui a um
            // mês
            if (productEntity.get().getProductType().equals(ProductType.SUBSCRIPTION)) {
                orderItemEntity.setFirstBillingDate(OffsetDateTime.now().plusDays(30));
            }

            // Se for um produto de subscrição, salvar no banco
            if (productEntity.get().getProductType().equals(ProductType.SUBSCRIPTION)) {

            }

            // Atualiza a referência do pedido em orderEntity
            orderItemEntity.setOrder(orderEntity);
            // Guarda o valor daquele instante em unitPrice (snapshot)
            orderItemEntity.setUnitPrice(productEntity.get().getPrice());

            orderEntity.setTotal(orderEntity.getTotal().add(calculateTotalPrice(productEntity, orderRequestedAmount)));

            productRepository.save(productEntity.get());
            log.info("Updated inventory for product {}", requestedProductId);
        }

        orderEntity.setOrderId(UUID.randomUUID().toString());
        orderEntity.setCreatedAt(OffsetDateTime.now());
        orderEntity.setUpdatedAt(OffsetDateTime.now());
        OrderEntity saved = orderRepository.save(orderEntity);
        log.info("Order {} saved for customer {} with {} items", saved.getOrderId(), saved.getCustomerId(),
                saved.getItems().size());
        return saved;
    }

    private void checkIfDigitalProductAndSendEmail(OrderEntity orderEntity, OrderItemEntity orderItemEntity,
            Optional<ProductEntity> productEntity) {
        if (productEntity.get().getProductType().equals(ProductType.DIGITAL)) {
            log.info(
                    "Email sent for customer {} with the activation key for Product {}",
                    orderEntity.getCustomerId(),
                    orderItemEntity.getProduct().getProductId());
        }
    }

    private void updateProductStockAccordingToProductType(OrderItemEntity orderItemEntity, String requestedProductId,
            int orderRequestedAmount,
            Optional<ProductEntity> productEntity) {

        Integer productStock;

        // para produtos físicos e quando for corporativo com quantidade especificada,
        // a quantidade está em stockQuantity
        if (orderItemEntity.getProduct().getProductType().equals(ProductType.PHYSICAL)
                || isProductCorporateWithStock(orderItemEntity)) {
            productStock = productEntity.get().getStockQuantity();
            productEntity.get().setStockQuantity(productStock - orderRequestedAmount);
            log.debug("Product {} stock decremented: availableBefore={} requested={}", requestedProductId,
                    productStock, orderRequestedAmount);
        }

        // para pré-venda a quantiadade está em preOrderSlots
        if (orderItemEntity.getProduct().getProductType().equals(ProductType.PRE_ORDER)) {
            productStock = productEntity.get().getPreOrderSlots();
            productEntity.get().setPreOrderSlots(productStock - orderRequestedAmount);
            log.debug("Product {} pre-order slots decremented: availableBefore={} requested={}", requestedProductId,
                    productStock, orderRequestedAmount);
        }

        // para digital a quantiadade está em licenses
        if (orderItemEntity.getProduct().getProductType().equals(ProductType.DIGITAL)) {
            productStock = productEntity.get().getLicenses();
            productEntity.get().setLicenses(productStock - orderRequestedAmount);
            // salvar a chave de ativacao
            orderItemEntity.setActivationKey(UUID.randomUUID().toString());
            log.debug("Product {} licenses decremented: availableBefore={} requested={}", requestedProductId,
                    productStock, orderRequestedAmount);
        }
    }

    // Um pedido corporativo não pode conter outros tipos de produtos
    private void checkIfOrderIsFullyCorporate(OrderEntity orderEntity) {
        int amountCorporativeItemsInOrder = 0;
        for (OrderItemEntity orderItemEntity : orderEntity.getItems()) {
            var productEntity = productRepository.findById(orderItemEntity.getProduct().getProductId());
            if (!productEntity.isEmpty() && productEntity.get().getProductType().equals(ProductType.CORPORATE)) {
                amountCorporativeItemsInOrder += 1;
            }
        }

        if (amountCorporativeItemsInOrder > 0 &&
                amountCorporativeItemsInOrder != orderEntity.getItems().size()) {
            throw new IllegalArgumentException(
                    "Mixed orders with corporative and non-corporative products are not allowed.");
        }
    }

    // Calcula o valor total do pedido. Se um pedido for corporativo e a quantia
    // pedia for maior ou igual a 100,
    // recebe um desconto de 15%
    private BigDecimal calculateTotalPrice(Optional<ProductEntity> productEntity, Integer orderRequestedAmount) {
        if (productEntity.get().getProductType().equals(ProductType.CORPORATE)
                && orderRequestedAmount >= 100) {
            return productEntity.get().getPrice()
                    .multiply(BigDecimal.valueOf(orderRequestedAmount))
                    .multiply(BigDecimal.valueOf(0.85)); // 15% disconto
        } else {
            return productEntity.get().getPrice()
                    .multiply(BigDecimal.valueOf(orderRequestedAmount));
        }
    }

    private boolean isProductCorporateWithStock(OrderItemEntity orderItemEntity) {
        return orderItemEntity.getProduct().getProductType().equals(ProductType.CORPORATE)
                && orderItemEntity.getQuantity() != null && orderItemEntity.getQuantity() > 0;
    }

}
