package com.brianstremel.bcnbproducts.product.controller;

import com.brianstremel.bcnbproducts.documentation.SwaggerExamples;
import com.brianstremel.bcnbproducts.product.exception.ErrorData;
import com.brianstremel.bcnbproducts.product.model.vo.GetProductPriceRequestVO;
import com.brianstremel.bcnbproducts.product.model.vo.GetProductPriceResponseVO;
import com.brianstremel.bcnbproducts.product.usecase.ProductPriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product Price", description = "API for managing product prices")
public class ProductPriceController {

    private final ProductPriceUseCase productPriceUseCase;

    @Operation(
            summary = "Get product price for specific time",
            description = "Retrieves the applicable product price based on the provided date",
            parameters = {
                    @Parameter(
                            name = "productId",
                            description = "Product identifier",
                            example = "35455",
                            in = ParameterIn.QUERY,
                            required = true
                    ),
                    @Parameter(
                            name = "brandId",
                            description = "Brand identifier",
                            example = "1",
                            in = ParameterIn.QUERY,
                            required = true
                    ),
                    @Parameter(
                            name = "applicationTime",
                            description = "Application date and time",
                            example = "2020-06-14T12:00:00.000+02:00",
                            in = ParameterIn.QUERY,
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved product price",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetProductPriceResponseVO.class),
                            examples = @ExampleObject(
                                    value = SwaggerExamples.PRODUCT_PRICE_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorData.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product price not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorData.class)
                    )
            )
    })
    @GetMapping("/prices/apply-time")
    ResponseEntity<GetProductPriceResponseVO> getProductPrice(@RequestParam @NotBlank final String productId,
                                                              @RequestParam @NotBlank final String brandId,
                                                              @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime applicationTime) {
        final var response = productPriceUseCase.getProductPrice(new GetProductPriceRequestVO(productId, brandId, applicationTime));
        return ResponseEntity.ok(response);
    }
}
