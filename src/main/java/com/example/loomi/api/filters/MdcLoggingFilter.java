package com.example.loomi.api.filters;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class MdcLoggingFilter extends OncePerRequestFilter {

    public static final String MDC_REQUEST_ID = "requestId";
    public static final String MDC_CUSTOMER_ID = "customerId";
    public static final String HEADER_REQUEST_ID = "X-Request-Id";
    public static final String HEADER_CUSTOMER_ID = "X-Customer-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // requestId: prefer header, otherwise generate
            String requestId = request.getHeader(HEADER_REQUEST_ID);
            if (requestId == null || requestId.isBlank()) {
                requestId = UUID.randomUUID().toString();
            }
            MDC.put(MDC_REQUEST_ID, requestId);
            response.setHeader(HEADER_REQUEST_ID, requestId);

            // customerId: prefer header, otherwise try to extract from path
            // /customers/{id}/...
            String customerId = request.getHeader(HEADER_CUSTOMER_ID);
            if ((customerId == null || customerId.isBlank()) && request.getRequestURI() != null) {
                String uri = request.getRequestURI();
                // simple extraction: look for "/customers/" and take next segment
                String marker = "/customers/";
                int idx = uri.indexOf(marker);
                if (idx != -1) {
                    int start = idx + marker.length();
                    int end = uri.indexOf('/', start);
                    if (end == -1) {
                        end = uri.length();
                    }
                    if (start < end) {
                        customerId = uri.substring(start, end);
                    }
                }
            }

            if (customerId != null && !customerId.isBlank()) {
                MDC.put(MDC_CUSTOMER_ID, customerId);
            }

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_REQUEST_ID);
            MDC.remove(MDC_CUSTOMER_ID);
        }
    }
}
