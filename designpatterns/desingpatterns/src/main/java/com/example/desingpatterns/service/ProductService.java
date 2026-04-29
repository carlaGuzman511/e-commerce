package com.example.desingpatterns.service;

import com.example.desingpatterns.interfaces.IProductService;
import com.example.desingpatterns.models.Product;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductService implements IProductService {

    @Override
    public Product getProductInfo(String name) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new Product(name, randomPrice());
    }

    private int randomPrice() {
        int min = 1000;
        int max = 9000;
        return (int) (Math.random() * (max - min + 1) + min);
    }
}