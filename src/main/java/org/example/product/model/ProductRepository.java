package org.example.product.model;

import java.util.*;

public class ProductRepository {

    private static final ProductRepository instance = new ProductRepository();


    private final Map<String, ProductModel> productMap = new HashMap<>();

    private ProductRepository() {
    }

    public static ProductRepository getInstance() {
        return instance;
    }

    public boolean addProduct(ProductModel productModel) {
        String response = productValidation(productModel);
        if (!"valid".equals(response)) {
            return false;
        }
        if (productMap.containsKey(productModel.getName())) {
            return false;
        }
        productMap.put(productModel.getName(), productModel);
        return true;
    }

    public boolean updateProduct(ProductModel newProductModel) {
        String message = productValidation(newProductModel);
        if (!"valid".equals(message)) {
            return false;
        }
        if (!productMap.containsKey(newProductModel.getName())) {
            return false;
        }
        productMap.put(newProductModel.getName(), newProductModel);
        return true;
    }

    public boolean deleteProduct(String name) {
        return productMap.remove(name) != null;
    }

    public Optional<ProductModel> searchToProduct(String name) {
        return Optional.ofNullable(productMap.get(name));
    }

    public Collection<ProductModel> getProducts() {
        return productMap.values();
    }

    private String productValidation(ProductModel productModel) {
        if (productModel.getName() == null || productModel.getName().isEmpty()) {
            return "The productModel name must not be empty. Please provide a valid name.";
        }
        if (productModel.getName().length() > 100) {
            return "The productModel name must not exceed 100 characters. Please provide a shorter name.";
        }
        if (productModel.getPrice() < 0) {
            return "The productModel price must be a non-negative value. Please provide a valid price.";
        }
        return "valid";
    }
}
