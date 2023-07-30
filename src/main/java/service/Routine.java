package service;
import exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import model.Product;
import org.springframework.stereotype.Service;
import repository.ProductRepository;
import exception.ProductExistsException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Routine implements IRoutine {
    private final ProductRepository productRepository;
    private String name;
    private List<Product> products;

    @Override
    public Product addProduct(Product product) {
        if(productExists(product.getName())){
            throw new ProductExistsException(product.getName()+ " has been added already!");
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product product, String name) {
        return productRepository.findByName(name).map(s -> {
            s.setCategory(product.getCategory());
            s.setName(product.getName());
            s.setBrand(product.getBrand());
            s.setSize(product.getSize());
            s.setPrice(product.getPrice());
            s.setDescription(product.getDescription());
            return productRepository.save(s);
        }).orElseThrow(() -> new ProductNotFoundException("This product does not already exist."));
    }

    @Override
    public Product getProduct(String name) {
        return productRepository.findByName(name)
                .orElseThrow(()-> new ProductNotFoundException("This product cannot be found"));
    }

    @Override
    public void deleteProduct(String name) {
        if(!productRepository.existsById(name)){
            throw new ProductNotFoundException("This product cannot be found");
        }
    }

    private boolean productExists(String name) {
        return productRepository.findByName(name).isPresent();
    }
}
