package com.example.desingpatterns.proxy;

import com.example.desingpatterns.interfaces.IProductService;
import com.example.desingpatterns.models.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServiceCacheProxy implements IProductService {

    private final IProductService realProductService;
    private final Map<String, Product> cache = new HashMap<>();

    public ProductServiceCacheProxy(@Qualifier("productService") IProductService realProductService) {
        this.realProductService = realProductService;
    }

    @Override
    public Product getProductInfo(String name) {
        if (cache.containsKey(name)) {
            System.out.println("Producto encontrado en cache: " + name);
            return cache.get(name);
        }

        System.out.println("El producto " + name + " no esta en cache, consultando al servicio principal");
        Product result = realProductService.getProductInfo(name);
        cache.put(name, result);

        return result;
    }
}