package bank.cardissuing.common.exception;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String traceId = Optional.ofNullable(request.getHeader("X-Trace-Id"))
//        X- prefix là convention cho custom HTTP headers (không phải standard header)
//        Trong distributed systems (microservices), khi Service A gọi Service B, Service A sẽ gửi header X-Trace-Id để cả 2 service dùng cùng trace ID
//        Điều này giúp correlate logs giữa nhiều services
//        Client → API Gateway (X-Trace-Id: abc123) → Card Service → Ledger Service
//                                              ↓               ↓
//                                        logs với abc123   logs với abc123
                .orElse(UUID.randomUUID().toString().substring(0, 8));
        //UUID đầy đủ: 550e8400-e29b-41d4-a716-446655440000 (36 ký tự)
        //Lấy 8 ký tự đầu: 550e8400 (đủ unique cho hầu hết cases)
        //Lý do: Log ngắn hơn, dễ đọc hơn trong console
        //Trade-off:
        //8 chars = 16^8 = 4.3 tỷ combinationsd → đủ unique cho 1 ứng dụng
        //Nếu cần cao hơn (distributed systems lớn), dùng full UUID hoặc 12 chars

        MDC.put("traceId", traceId);
        MDC.put("path", request.getRequestURI());
        MDC.put("method", request.getMethod());
        try{
            filterChain.doFilter(request, response);
        }finally{
            MDC.clear();
        }
    }
}
