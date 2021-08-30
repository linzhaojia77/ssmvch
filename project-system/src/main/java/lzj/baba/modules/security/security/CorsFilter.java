package lzj.baba.modules.security.security;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.cors.*;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: TODO
 * @author: lzj
 * @date: 2021年08月03日 15:31
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

    private final CorsConfigurationSource configSource;

    private CorsProcessor processor = new DefaultCorsProcessor();


    /**
     * Constructor accepting a {@link CorsConfigurationSource} used by the filter
     * to find the {@link CorsConfiguration} to use for each incoming request.
     * @see UrlBasedCorsConfigurationSource
     */
    public CorsFilter(CorsConfigurationSource configSource) {
        Assert.notNull(configSource, "CorsConfigurationSource must not be null");
        this.configSource = configSource;
    }


    /**
     * Configure a custom {@link CorsProcessor} to use to apply the matched
     * {@link CorsConfiguration} for a request.
     * <p>By default {@link DefaultCorsProcessor} is used.
     */
    public void setCorsProcessor(CorsProcessor processor) {
        Assert.notNull(processor, "CorsProcessor must not be null");
        this.processor = processor;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(request);
        boolean isValid = this.processor.processRequest(corsConfiguration, request, response);
        if (!isValid || CorsUtils.isPreFlightRequest(request)) {
            return;
        }
        filterChain.doFilter(request, response);
    }

}