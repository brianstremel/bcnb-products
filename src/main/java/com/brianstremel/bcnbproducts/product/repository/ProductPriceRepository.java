package com.brianstremel.bcnbproducts.product.repository;

import com.brianstremel.bcnbproducts.product.model.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    @Query("""
        SELECT p FROM ProductPrice p 
        WHERE p.productId = :productId 
        AND p.brandId = :brandId 
        AND :applicationTime BETWEEN p.startDate AND p.endDate 
        ORDER BY p.priority DESC 
        LIMIT 1
        """)
    Optional<ProductPrice> findTopByProductIdAndBrandIdAndDateBetweenOrderByPriorityDesc(
            @Param("productId") String productId,
            @Param("brandId") String brandId,
            @Param("applicationTime") LocalDateTime applicationTime
    );
}
