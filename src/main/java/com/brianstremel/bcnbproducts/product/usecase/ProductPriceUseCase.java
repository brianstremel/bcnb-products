package com.brianstremel.bcnbproducts.product.usecase;

import com.brianstremel.bcnbproducts.product.model.entity.ProductPrice;
import com.brianstremel.bcnbproducts.product.model.vo.GetProductPriceRequestVO;
import com.brianstremel.bcnbproducts.product.model.vo.GetProductPriceResponseVO;

import java.util.List;

public interface ProductPriceUseCase {

    GetProductPriceResponseVO getProductPrice(final GetProductPriceRequestVO request);
    List<ProductPrice> getProductPriceList();
}
