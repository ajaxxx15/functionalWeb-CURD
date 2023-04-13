package com.demo.functionalweb.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;

import java.util.Map;

@Configuration
public class ExceptionConf {

    @Bean
    @Order(-2)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public GlobalErrorWebExceptionHandler reactiveExceptionHandler(WebProperties webProperties, ApplicationContext applicationContext, ServerCodecConfigurer configure) {
        GlobalErrorWebExceptionHandler exceptionHandler = new GlobalErrorWebExceptionHandler(
                new DefaultErrorAttributes(), webProperties.getResources(), applicationContext, exceptionToStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR
        );
        exceptionHandler.setMessageWriters(configure.getWriters());
        exceptionHandler.setMessageReaders(configure.getReaders());
        return exceptionHandler;
    }
    @Bean
    public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
        return Map.of(
                ProductNotFoundException.class, HttpStatus.NOT_FOUND,
                GenralException.class, HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
