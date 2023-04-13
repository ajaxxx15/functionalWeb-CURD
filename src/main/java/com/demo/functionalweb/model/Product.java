package com.demo.functionalweb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("products")
@ToString
public class Product {
	@Id
	private String id;
	private String name;
	private int quantity;
	private double price;
}