package com.example.desingpatterns.controllers;


import com.example.desingpatterns.interfaces.ProductComponent;
import com.example.desingpatterns.service.CatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog")
    public String showCatalog() {
        ProductComponent root = catalogService.createCatalog();

        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        java.io.PrintStream printStream = new java.io.PrintStream(outputStream);
        System.setOut(printStream);

        root.showDetails("");
        System.out.println("\nTotal catalog value: $" + root.getPrice());

        return outputStream.toString();
    }
}
