package com.example.desingpatterns.controllers;

import com.example.desingpatterns.interfaces.IProductService;
import com.example.desingpatterns.models.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(@Qualifier("productServiceCacheProxy") IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{name}")
    public Product getProduct(@PathVariable String name) {
        return productService.getProductInfo(name);
    }
}