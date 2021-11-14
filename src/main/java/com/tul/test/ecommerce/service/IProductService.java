package com.tul.test.ecommerce.service;

import com.tul.test.ecommerce.model.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(Product product);
    Product updateProduct(Product product, Integer id);
    List<Product> getProducts();
    void deleteProduct(Integer id);
}
