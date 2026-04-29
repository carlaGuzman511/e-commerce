package com.example.desingpatterns.models;

import com.example.desingpatterns.interfaces.ProductComponent;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory implements ProductComponent {
    private final String name;
    private final List<ProductComponent> components = new ArrayList<>();

    public ProductCategory(String name) {
        this.name = name;
    }

    public void addComponent(ProductComponent component) {
        components.add(component);
    }

    public void removeComponent(ProductComponent component) {
        components.remove(component);
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getPrice() {
        return components.stream().mapToDouble(ProductComponent::getPrice).sum();
    }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "+ Category: " + name);
        for (ProductComponent component : components) {
            component.showDetails(indent + "   ");
        }
    }
}
