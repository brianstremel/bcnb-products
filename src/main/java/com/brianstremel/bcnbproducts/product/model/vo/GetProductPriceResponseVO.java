package com.brianstremel.bcnbproducts.product.model.vo;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record GetProductPriceResponseVO(String productId,
                                        String brandId,
                                        ZonedDateTime applicationTime,
                                        String priceList,
                                        BigDecimal price,
                                        String currency) {
}
