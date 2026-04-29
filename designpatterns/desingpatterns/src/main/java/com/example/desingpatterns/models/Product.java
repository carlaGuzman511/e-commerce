package com.example.desingpatterns.models;

import com.example.desingpatterns.interfaces.ProductComponent;

public class Product implements ProductComponent {
    private final String name;
    private final double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getPrice() { return price; }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "- Product: " + name + " ($" + price + ")");
    }
}
