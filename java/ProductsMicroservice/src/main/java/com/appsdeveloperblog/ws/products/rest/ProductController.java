package com.appsdeveloperblog.ws.products.rest;

import com.appsdeveloperblog.ws.products.model.CreateProductRestModel;
import com.appsdeveloperblog.ws.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRestModel productRestModel) {
        String productId = productService.createProduct(productRestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}