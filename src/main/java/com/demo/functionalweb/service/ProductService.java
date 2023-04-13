package com.demo.functionalweb.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.demo.functionalweb.dto.ProductDto;
import com.demo.functionalweb.exception.GenralException;
import com.demo.functionalweb.exception.ProductNotFoundException;
import com.demo.functionalweb.repository.ProductRepository;
import com.demo.functionalweb.util.AppUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getAllProduct() {

        return productRepository.findAll().switchIfEmpty(Flux.error(new ProductNotFoundException()))
                .map(AppUtil::entityToDto)
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage()));

    }

    public Flux<ProductDto> getProduct(ServerRequest request) {
        String name = request.pathVariable("name");

        return productRepository.findByName(name).switchIfEmpty(Flux.error(new ProductNotFoundException()))
                .map(AppUtil::entityToDto)
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage()));

    }

    public Mono<ProductDto> saveProduct(ServerRequest request) {
        Mono<ProductDto> productDtoRequest = request.bodyToMono(ProductDto.class);
        return productDtoRequest.map(productDto -> {
            productDto.setId(UUID.randomUUID().toString());
            return productDto;
        }).map(AppUtil::dtoToEntity).flatMap(productRepository::save).map(AppUtil::entityToDto)
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage()));

    }

    public Mono<ProductDto> deleteProduct(ServerRequest request) {
        String id = request.pathVariable("id");

        return productRepository.findById(id).switchIfEmpty(Mono.error(new ProductNotFoundException(id)))
                .flatMap(existingProduct -> productRepository.delete(existingProduct)
                        .then(Mono.just(AppUtil.entityToDto(existingProduct))))
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage()));
    }

    public Mono<ProductDto> updateProduct(ServerRequest request) {
        Mono<ProductDto> productDtoRequest = request.bodyToMono(ProductDto.class);
        return productDtoRequest.map(productDto -> {
            return productRepository.findById(productDto.getId()).switchIfEmpty(Mono.error(new ProductNotFoundException(productDto.getId())))
                    .flatMap(existingProduct -> {
                        if (null != productDto.getName())
                            existingProduct.setName(productDto.getName());
                        if (productDto.getPrice() > 0)
                            existingProduct.setPrice(productDto.getPrice());

                        if (productDto.getQuantity() >= 0) {
                            existingProduct.setQuantity(productDto.getQuantity());
                        }
                        return productRepository.save(existingProduct);
                    }).map(AppUtil::entityToDto)
                    .onErrorMap(ex -> {
                        log.error("Exception Occured :" + ex);
                        throw new GenralException(ex.getMessage());
                    });
        }).flatMap(returnMono -> {
            return returnMono;
        }).onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage()));
    }
}
