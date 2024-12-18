package org.example.product.model;

import java.util.*;

public class Store {

    private static final Store instance = new Store();

    private final Set<Product> products = new TreeSet<>(Comparator.comparing(Product::getName));

    public Store() {
    }

    public static Store getInstance() {
        return instance;
    }

    public boolean addProduct(Product product) {
        String message = productValidation(product);

        if (!"valid".equals(message)) {
            return false;
        }

        if (products.contains(product)) {
            return false;
        }

        products.add(product);
        return true;
    }

    public boolean updateProduct(Product newProduct) {
        String message = productValidation(newProduct);

        if (!"valid".equals(message)) {
            return false;
        }

        Optional<Product> oldProduct = getProduct(newProduct.getName());

        if (oldProduct.isEmpty()) {
            return false;
        }

        products.remove(oldProduct.get());
        products.add(newProduct);
        return true;
    }

    public boolean deleteProduct(String name) {
        Optional<Product> product = getProduct(name);

        if (product.isEmpty()) {
            return false;
        }

        products.remove(product.get());
        return true;
    }

    public Optional<Product> searchToProduct(String name) {
        return getProduct(name);
    }

    public Set<Product> getProducts() {
        return products;
    }

    private Optional<Product> getProduct(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    private String productValidation(Product product) {
        String productName = product.getName();
        double productPrice = product.getPrice();

        if (productName == null || productName.isEmpty())
            return "The product name must not be empty. Please provide a valid name.";
        if (productName.length() > 100)
            return "The product name must not exceed 100 characters. Please provide a shorter name.";
        if (productPrice < 0)
            return "The product price must be a non-negative value. Please provide a valid price.";
        return "valid";
    }
}
