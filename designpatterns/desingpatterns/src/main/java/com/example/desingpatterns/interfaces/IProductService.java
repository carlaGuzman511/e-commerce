package com.example.desingpatterns.interfaces;

import com.example.desingpatterns.models.Product;

public interface IProductService {
    Product getProductInfo(String name);
}