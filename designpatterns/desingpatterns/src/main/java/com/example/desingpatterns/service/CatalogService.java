package com.example.desingpatterns.service;

import com.example.desingpatterns.interfaces.ProductComponent;
import com.example.desingpatterns.models.Product;
import com.example.desingpatterns.models.ProductCategory;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    public ProductComponent createCatalog() {
        // Main Category
        ProductCategory electronics = new ProductCategory("Electronics");

        // Sub-Categories
        ProductCategory smartphones = new ProductCategory("Smartphones");
        ProductCategory laptops = new ProductCategory("Laptops");

        // Products
        Product iphone = new Product("iPhone 15", 1200);
        Product samsung = new Product("Samsung Galaxy S24", 1000);
        Product macbook = new Product("MacBook Pro", 2500);
        Product dell = new Product("Dell XPS", 2000);

        // Build hierarchy
        smartphones.addComponent(iphone);
        smartphones.addComponent(samsung);

        laptops.addComponent(macbook);
        laptops.addComponent(dell);

        electronics.addComponent(smartphones);
        electronics.addComponent(laptops);

        return electronics;
    }
}
