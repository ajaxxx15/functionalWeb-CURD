package com.demo.functionalweb.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.demo.functionalweb.service.ProductService;
import com.demo.functionalweb.dto.ProductDto;

import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

    @Autowired
    private ProductService productService;

    public Mono<ServerResponse> getAllProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.getAllProduct(),
                ProductDto.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.getProduct(request),
                ProductDto.class);

    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.saveProduct(request),
                ProductDto.class);

    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.deleteProduct(request),
                ProductDto.class);

    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.updateProduct(request),
                ProductDto.class);

    }
}
