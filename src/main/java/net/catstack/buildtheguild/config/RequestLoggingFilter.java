package net.catstack.buildtheguild.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var sb = new StringBuilder();
        sb.append("-----------------------------------------\n");
        sb.append("REQUEST: " + request.getRequestURI() + "\nHEADERS: \n");
        var headersIterator = request.getHeaderNames().asIterator();;
        headersIterator.forEachRemaining(header -> {
            sb.append(header + ": " + request.getHeader(header) + "\n");
        });
        sb.append("-----------------------------------------");

        log.info(sb.toString());
        filterChain.doFilter(request, response);
    }
}
