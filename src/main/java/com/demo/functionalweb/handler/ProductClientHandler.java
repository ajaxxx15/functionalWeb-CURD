package com.demo.functionalweb.handler;

import com.demo.functionalweb.dto.ProductDto;
import com.demo.functionalweb.service.ProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductClientHandler {

    @Autowired
    private ProductClientService productClientService;

    public Mono<ServerResponse> getAllProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productClientService.getAllProduct(),
                ProductDto.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productClientService.getProduct(request),
                ProductDto.class);

    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productClientService.saveProduct(request),
                ProductDto.class);

    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productClientService.deleteProduct(request),
                ProductDto.class);

    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productClientService.updateProduct(request),
                ProductDto.class);

    }
}
