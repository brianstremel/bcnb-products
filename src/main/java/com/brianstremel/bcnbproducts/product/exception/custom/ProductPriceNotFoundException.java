package com.brianstremel.bcnbproducts.product.exception.custom;



public class ProductPriceNotFoundException extends RuntimeException {

    public ProductPriceNotFoundException(final String message) {
        super(message);
    }
}
