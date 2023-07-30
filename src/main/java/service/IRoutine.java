package service;

import model.Product;

import java.util.List;

public interface IRoutine {
    Product addProduct(Product product);
    List<Product> getProducts();
    Product updateProduct(Product product, String name);
    Product getProduct(String name);
    void deleteProduct(String name);
}
