package com.demo.functionalweb.util;

import org.springframework.beans.BeanUtils;

import com.demo.functionalweb.dto.ProductDto;
import com.demo.functionalweb.model.Product;

public class AppUtil {
	
	public static ProductDto entityToDto(Product product) {
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}

	public static Product dtoToEntity(ProductDto productDto) {
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		return product;
	}

}
