package com.tul.test.ecommerce.controller;

import com.tul.test.ecommerce.model.Product;
import com.tul.test.ecommerce.service.IProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/apirest/product")
@Api(value="ProductResource", description="Controller that handles Product requests")
public class ProductController {

    private final IProductService iProductService;

    @Autowired
    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        if(result.hasErrors()) {
            return this.validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iProductService.saveProduct(product));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product, BindingResult result, @PathVariable Integer id) {
        if(result.hasErrors()) {
            return this.validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iProductService.updateProduct(product, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        iProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> listProducts() {
        return ResponseEntity.ok().body(iProductService.getProducts());
    }

    private ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "Field " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
