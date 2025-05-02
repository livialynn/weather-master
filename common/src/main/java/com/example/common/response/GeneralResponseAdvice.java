package com.example.common.response;

import com.example.common.domain.GeneralResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@RestControllerAdvice
public class GeneralResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String accept = request.getHeader("Accept");

        // Debug
        System.out.println("Intercepting URI: " + uri + ", Accept: " + accept);

        // Skip response wrapping for Prometheus or non-JSON requests
        if (uri.startsWith("/actuator/prometheus") ||
                uri.startsWith("/actuator") ||
                uri.startsWith("/swagger") ||
                uri.startsWith("/v3/api-docs") ||
                uri.startsWith("/swagger-ui") ||
                uri.startsWith("/v3/api-docs/swagger-config") ||
                uri.startsWith("/webjars")
                ) {
            return false;
        }
        if (accept != null && !accept.contains("application/json")) {
            return false;
        }


        return true;
    }


    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setCode(0);
        generalResponse.setData(body);
        generalResponse.setTimestamp(new Date());
        return generalResponse;
    }
}
