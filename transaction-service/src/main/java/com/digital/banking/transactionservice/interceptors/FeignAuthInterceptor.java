package com.digital.banking.transactionservice.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("credentials from client " + auth.getCredentials());
        if (auth != null && auth.getCredentials() instanceof String token) {
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }
    }
}
