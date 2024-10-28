package com.brianstremel.bcnbproducts.product.model.vo;

import java.time.ZonedDateTime;

public record GetProductPriceRequestVO(String productId,
                                       String brandId,
                                       ZonedDateTime applicationTime) {


}
