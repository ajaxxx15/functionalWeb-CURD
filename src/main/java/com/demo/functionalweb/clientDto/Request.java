package com.demo.functionalweb.clientDto;

import com.demo.functionalweb.dto.ProductDto;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private List<ProductDto> productDto;
}
