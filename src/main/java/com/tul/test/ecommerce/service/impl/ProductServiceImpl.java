package com.tul.test.ecommerce.service.impl;

import com.tul.test.ecommerce.exceptions.EcommerceBusinessException;
import com.tul.test.ecommerce.model.Product;
import com.tul.test.ecommerce.repository.IProductRepository;
import com.tul.test.ecommerce.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private static final String DISCOUNT_PRODUCT_TYPE = "descuento";
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final IProductRepository iProductRepository;

    @Autowired
    public ProductServiceImpl(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        Optional<Product> productOptional = iProductRepository.findProductBySku(product.getSku());
        if (productOptional.isPresent()) {
            logger.info("------Error method: saveProduct, product already registered-------");
            throw new EcommerceBusinessException("product already registered", HttpStatus.BAD_REQUEST.value());
        }
        if (product.getProductType().equals(DISCOUNT_PRODUCT_TYPE)) {
            product.setPrice(product.getPrice() / 2);
        }
        return iProductRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Product product, Integer id) {
        logger.info("-------updateProduct call has started-------");
        Optional<Product> productById = iProductRepository.findById(id);
        if (!productById.isPresent()) {
            logger.info("------Error method: updateProduct, product doesn't exist-------");
            throw new EcommerceBusinessException("product doesn't exist", HttpStatus.NOT_FOUND.value());
        } else {
            Optional<Product> productBySku = iProductRepository.findBySkuWithDistinctId(id, product.getSku());
            if (productBySku.isPresent()) {
                logger.info("------Error method: updateProduct, Sku already assigned to another product-------");
                throw new EcommerceBusinessException("Sku already assigned to another product", HttpStatus.BAD_REQUEST.value());
            }
        }
        Product prod = productById.get();
        prod.setName(product.getName());
        prod.setSku(product.getSku());
        prod.setDescription(product.getDescription());
        prod.setPrice(product.getPrice());
        Product productUpdate = iProductRepository.save(prod);
        logger.info("-------updateProduct call has ended-------");
        return productUpdate;
    }

    @Override
    public List<Product> getProducts() {
        return iProductRepository.findAll();
    }

    @Override
    public void deleteProduct(Integer id) {
        logger.info("-------deleteProduct call has started-------");
        Optional<Product> productOptional = iProductRepository.findById(id);
        if (!productOptional.isPresent()) {
            logger.info("------Error method: updateProduct, product doesn't exist-------");
            throw new EcommerceBusinessException("product doesn't exist", HttpStatus.NOT_FOUND.value());
        }
        iProductRepository.deleteById(id);
        logger.info("-------deleteProduct call has ended-------");
    }

}
