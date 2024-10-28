package com.brianstremel.bcnbproducts.product.service;

import com.brianstremel.bcnbproducts.product.exception.custom.ProductPriceNotFoundException;
import com.brianstremel.bcnbproducts.product.model.entity.ProductPrice;
import com.brianstremel.bcnbproducts.product.model.vo.GetProductPriceRequestVO;
import com.brianstremel.bcnbproducts.product.model.vo.GetProductPriceResponseVO;
import com.brianstremel.bcnbproducts.product.repository.ProductPriceRepository;
import com.brianstremel.bcnbproducts.product.usecase.ProductPriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPriceService implements ProductPriceUseCase {

    final ProductPriceRepository productPriceRepository;


    @Override
    public GetProductPriceResponseVO getProductPrice(final GetProductPriceRequestVO request) {
        return productPriceRepository.findTopByProductIdAndBrandIdAndDateBetweenOrderByPriorityDesc(request.productId(),
                        request.brandId(),
                        request.applicationTime().toLocalDateTime()
                )
                .map(price -> new GetProductPriceResponseVO(
                        request.productId(),
                        request.brandId(),
                        request.applicationTime(),
                        price.getPriceList().toString(),
                        price.getPrice(),
                        price.getCurrency()))
                .orElseThrow(() -> new ProductPriceNotFoundException("No product price found for the given criteria"));
    }

    @Override
    public List<ProductPrice> getProductPriceList() {
        return productPriceRepository.findAll();
    }
}
