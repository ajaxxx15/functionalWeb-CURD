package com.demo.functionalweb.config;

import com.demo.functionalweb.clientDto.Request;
import com.demo.functionalweb.dto.ProductDto;
import com.demo.functionalweb.handler.ProductClientHandler;
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
public class ProductWebClientRouter {

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/functional/client/getallproduct",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = ProductClientHandler.class,
                            beanMethod = "getAllProduct",
                            operation = @Operation(
                                    operationId = "getAllProduct",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Request.class
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/functional/client/getproduct/{name}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = ProductClientHandler.class,
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
                            path = "/functional/client/save",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = ProductClientHandler.class,
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
                            path = "/functional/client/delete/{id}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.DELETE,
                            beanClass = ProductClientHandler.class,
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
                            path = "/functional/client/update",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.PUT,
                            beanClass = ProductClientHandler.class,
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
    public RouterFunction<ServerResponse> routeClient(ProductClientHandler productClientHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/functional/client/getallproduct")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productClientHandler::getAllProduct)
                .andRoute(RequestPredicates.GET("/functional/client/getproduct/{name}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productClientHandler::getProduct)
                .andRoute(RequestPredicates.POST("/functional/client/save")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productClientHandler::saveProduct)
//
                .andRoute(RequestPredicates.DELETE("/functional/client/delete/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productClientHandler::deleteProduct)
                .andRoute(RequestPredicates.PUT("/functional/client/update")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), productClientHandler::updateProduct);
    }


}
