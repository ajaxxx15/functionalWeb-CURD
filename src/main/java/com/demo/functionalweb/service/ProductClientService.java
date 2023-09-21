package com.demo.functionalweb.service;

import com.demo.functionalweb.clientDto.Request;
import com.demo.functionalweb.clientDto.Response;
import com.demo.functionalweb.dto.ProductDto;
import com.demo.functionalweb.exception.GenralException;
import com.demo.functionalweb.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
public class ProductClientService {
    @Autowired
    private WebClient webClient;

    public Flux<ProductDto> getAllProduct() {
        return webClient.get().uri("product/getallproduct")
                .retrieve()
                .bodyToMono(Response.class)
                .map(res -> res.getProductDto())
                .flatMapMany(Flux::fromIterable)
//                .flatMapIterable(list -> list)
                .log();
    }

    public Flux<ProductDto> getProduct(ServerRequest serverRequest) {
        String name = serverRequest.pathVariable("name");
        return webClient.get().uri("product/get/" + name)
                .retrieve()
                .bodyToMono(Response.class)
                .map(res -> res.getProductDto())
//                .flatMapMany(Flux::fromIterable)
                .flatMapIterable(list -> list)
                .log();
    }

    public Mono<ProductDto> saveProduct(ServerRequest serverRequest) {
        Mono<ProductDto> productDtoMono = serverRequest.bodyToMono(ProductDto.class);
        Mono<Request> requestMono = productDtoMono.map(productDto -> {
            Request request = new Request();
            request.setProductDto(Arrays.asList(productDto));
            return request;
        });
        return requestMono.map(request ->
                webClient.post().uri("product/save")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(Response.class)
                        .map(res -> res.getProductDto().get(0))
                        .log()
        ).flatMap(result -> result);
    }

    public Mono<ProductDto> updateProduct(ServerRequest serverRequest) {
        Mono<ProductDto> productDtoMono = serverRequest.bodyToMono(ProductDto.class);
        Mono<Request> requestMono = productDtoMono.map(productDto -> {
            Request request = new Request();
            request.setProductDto(Arrays.asList(productDto));
            return request;
        });

        return requestMono.map(request ->
                webClient.put().uri("product/update")
                        .bodyValue(request)
                        .retrieve()
                        .onStatus(HttpStatus::isError, response -> {
                            logTraceResponse(log, response);
                            if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                return Mono.error(new ProductNotFoundException(request.getProductDto().get(0).getId()));
                            } else {
                                System.out.println("m i here " + response.statusCode());
                                Mono<Response> responseMono = response.bodyToMono(Response.class);
                                return responseMono.map(errMessage -> {
                                    return new GenralException(errMessage.getErrorMessage());
                                });
                            }
                        })
                        .bodyToMono(Response.class)
                        .map(res -> res.getProductDto().get(0))
                        .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage()))
                        .log()
        ).flatMap(result -> result);
    }

    public Mono<ProductDto> deleteProduct(ServerRequest serverRequest) {
        try {
            String id = serverRequest.pathVariable("id");
            return webClient.delete().uri("product/delete/" + id)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response ->
                    {
                        if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                            return Mono.error(new ProductNotFoundException(id));
                        } else {
                            Mono<Response> responseMono = response.bodyToMono(Response.class);
                            return responseMono.map(errMessage -> {
                                return new GenralException(errMessage.getErrorMessage());
                            });
                        }
                    })
                    .bodyToMono(Response.class)
                    .map(res -> res.getProductDto().get(0))
                    .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage()))
                    .log();
        } catch (WebClientResponseException wcre) {
            log.error("Error Response Code is {} and Response Body is {}"
                    , wcre.getRawStatusCode(), wcre.getResponseBodyAsString());
            log.error("Exception in method addInvoice()", wcre);
            throw wcre;
        } catch (Exception ex) {
            log.error("Exception in method addInvoice()", ex);
            throw ex;
        }
    }

    public static void logTraceResponse(Logger log, ClientResponse response) {
        if (true) {
//        if (log.isTraceEnabled()) {
            log.trace("Response status: {}", response.statusCode());
            log.trace("Response headers: {}", response.headers().asHttpHeaders());
            response.bodyToMono(String.class)
                    .subscribe(body -> log.trace("Response body: {}", body));
        }
    }

}
