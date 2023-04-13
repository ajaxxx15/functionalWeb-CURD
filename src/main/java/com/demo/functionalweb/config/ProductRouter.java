package com.demo.functionalweb.config;

import com.demo.functionalweb.dto.ProductDto;
import com.demo.functionalweb.handler.ProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/functional/getallproduct",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = ProductHandler.class,
                            beanMethod = "getAllProduct",
                            operation = @Operation(
                                    operationId = "getAllProduct",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = ProductDto.class
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/functional/getproduct/{name}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = ProductHandler.class,
                            beanMethod = "getProduct",

                            operation = @Operation(
                                    operationId = "getProduct",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = ProductDto.class
                                                    ))
                                            ),
                                            @ApiResponse(responseCode = "404")
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "name")
                                    }

                            )
                    ),
                    @RouterOperation(
                            path = "/functional/save",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = ProductHandler.class,
                            beanMethod = "saveProduct",
                            operation = @Operation(
                                    operationId = "saveProduct",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = ProductDto.class
                                                    ))
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = ProductDto.class
                                            ))
                                    )

                            )

                    ),
                    @RouterOperation(
                            path = "/functional/delete/{id}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.DELETE,
                            beanClass = ProductHandler.class,
                            beanMethod = "deleteProduct",
                            operation = @Operation(
                                    operationId = "deleteProduct",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = ProductDto.class
                                                    ))
                                            ),
                                            @ApiResponse(responseCode = "404")
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "id")
                                    }

                            )
                    ),
                    @RouterOperation(
                            path = "/functional/update",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.PUT,
                            beanClass = ProductHandler.class,
                            beanMethod = "updateProduct",
                            operation = @Operation(
                                    operationId = "updateProduct",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = ProductDto.class
                                                    ))
                                            ),
                                            @ApiResponse(responseCode = "404")
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = ProductDto.class
                                            ))
                                    )
                            )
                    )

            }
    )
    public RouterFunction<ServerResponse> route(ProductHandler productHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/functional/getallproduct")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productHandler::getAllProduct)
                .andRoute(RequestPredicates.GET("/functional/getproduct/{name}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productHandler::getProduct)
                .andRoute(RequestPredicates.POST("/functional/save")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productHandler::saveProduct)

                .andRoute(RequestPredicates.DELETE("/functional/delete/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productHandler::deleteProduct)
                .andRoute(RequestPredicates.PUT("/functional/update")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productHandler::updateProduct);
    }


}
