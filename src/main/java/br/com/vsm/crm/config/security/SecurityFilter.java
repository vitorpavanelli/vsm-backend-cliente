package br.com.vsm.crm.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@ConditionalOnExpression("{'prod', 'dev'}.contains('${app.dist}')")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class SecurityFilter implements Filter {

    @Value("${auth.server1}")
    private String authorizationServer1;

    @Value("${app.dist}")
    private String env;

    @Autowired
    private RestTemplate restTemplate;

    private static HttpHeaders headers;

    static {
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String origin = request.getHeader("origin");
        boolean isPostman = (env.equals("dev") && request.getHeader("postman-token") != null);

        response.setHeader("Access-Control-Allow-Origin", isPostman ? "*" : origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Cache-Control, Content-Type, x-xsrf-token");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            if (request.getHeader("Authorization") == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            try {
                headers.set("Authorization", request.getHeader("Authorization"));
                final ResponseEntity<AccessGrants> exchange = restTemplate.exchange(
                        authorizationServer1,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        AccessGrants.class
                );

                if (exchange.getStatusCode() != HttpStatus.OK) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                AccessGrants accessGrants = exchange.getBody();
                if (accessGrants == null) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                if (!accessGrants.isAdmin()) {
                    if (accessGrants.getAccesses() == null || accessGrants.getAccesses().size() == 0) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }

                    String _URI = request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/"));
                    String access = accessGrants.getAccesses().stream()
                            .filter(item -> "/api/admin/access".equals(item))
                            .findAny()
                            .orElse(null);

                    if (access == null) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }

            } catch (RestClientException e) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;

            }

            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        }
    }
}