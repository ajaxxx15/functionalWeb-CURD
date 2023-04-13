package com.demo.functionalweb.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.demo.functionalweb.model.Product;

import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCassandraRepository<Product, String> {
	
	@AllowFiltering
	Flux<Product> findByName(String name);

}
