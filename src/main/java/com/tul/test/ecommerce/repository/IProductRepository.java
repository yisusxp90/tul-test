package com.tul.test.ecommerce.repository;

import com.tul.test.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer>  {
    Optional<Product> findProductBySku(String sku);

    @Query(value="select * from products where id !=?1 and sku =?2", nativeQuery = true)
    Optional<Product> findBySkuWithDistinctId(Integer id, String sku);
}
