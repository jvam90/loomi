-- ============================================================
-- 1. Habilita extensão necessária para criar DB condicionalmente
-- ============================================================
CREATE EXTENSION IF NOT EXISTS dblink;

-- ============================================================
-- 2. Cria Banco de Dados "loomi" apenas se não existir
-- ============================================================
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'loomi') THEN
        PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE loomi');
    END IF;
END
$$ LANGUAGE plpgsql;

-- ============================================================
-- 3. Conectar ao banco loomi (necessário rodar manualmente em alguns clientes)
-- ============================================================
-- \c loomi

-- ============================================================
-- 4. Criar tabela products se não existir
-- ============================================================
CREATE TABLE IF NOT EXISTS products (
  product_id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  product_type VARCHAR(20) NOT NULL, -- PHYSICAL, SUBSCRIPTION, DIGITAL, PRE_ORDER, CORPORATE
  price DECIMAL(10, 2) NOT NULL,
  stock_quantity INTEGER,
  active BOOLEAN DEFAULT true,
  metadata JSONB
);

-- ============================================================
-- 5. Inserir todos os produtos (UM ÚNICO INSERT)
-- ============================================================
INSERT INTO products (product_id, name, product_type, price, stock_quantity, metadata)
VALUES
-- PHYSICAL
('BOOK-CC-001', 'Clean Code', 'PHYSICAL', 89.90, 150, '{}'::jsonb),
('LAPTOP-PRO-2024', 'Laptop Pro', 'PHYSICAL', 5499.00, 8, '{}'::jsonb),
('LAPTOP-MBP-M3-001', 'MacBook Pro M3', 'PHYSICAL', 12999.00, 25, '{}'::jsonb),

-- SUBSCRIPTION
('SUB-PREMIUM-001', 'Premium Monthly', 'SUBSCRIPTION', 49.90, NULL, '{}'::jsonb),
('SUB-BASIC-001', 'Basic Monthly', 'SUBSCRIPTION', 19.90, NULL, '{}'::jsonb),
('SUB-ENTERPRISE-001', 'Enterprise Plan', 'SUBSCRIPTION', 299.00, NULL, '{}'::jsonb),
('SUB-ADOBE-CC-001', 'Adobe Creative Cloud', 'SUBSCRIPTION', 159.00, NULL, '{}'::jsonb),

-- DIGITAL
('EBOOK-JAVA-001', 'Effective Java', 'DIGITAL', 39.90, NULL, '{"licenses": 1000}'::jsonb),
('EBOOK-DDD-001', 'Domain-Driven Design', 'DIGITAL', 59.90, NULL, '{"licenses": 500}'::jsonb),
('EBOOK-SWIFT-001', 'Swift Programming', 'DIGITAL', 49.90, NULL, '{"licenses": 800}'::jsonb),
('COURSE-KAFKA-001', 'Kafka Mastery', 'DIGITAL', 299.00, NULL, '{"licenses": 500}'::jsonb),

-- PRE_ORDER
('GAME-2025-001', 'Epic Game 2025', 'PRE_ORDER', 249.90, NULL, '{"releaseDate": "2025-06-01", "preOrderSlots": 1000}'::jsonb),
('PRE-PS6-001', 'PlayStation 6', 'PRE_ORDER', 4999.00, NULL, '{"releaseDate": "2025-11-15", "preOrderSlots": 500}'::jsonb),
('PRE-IPHONE16-001', 'iPhone 16 Pro', 'PRE_ORDER', 7999.00, NULL, '{"releaseDate": "2025-09-20", "preOrderSlots": 2000}'::jsonb),

-- CORPORATE
('CORP-LICENSE-ENT', 'Enterprise License', 'CORPORATE', 15000.00, NULL, '{}'::jsonb),
('CORP-CHAIR-ERG-001', 'Ergonomic Chair Bulk', 'CORPORATE', 899.00, 500, '{}'::jsonb)
ON CONFLICT (product_id) DO NOTHING;
-- Opcional: evita erro caso já exista
