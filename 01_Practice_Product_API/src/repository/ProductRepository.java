package repository;

import model.Product;
import model.DigitalProduct;
import model.PhysicalProduct;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new PhysicalProduct(1L,
                "Keyboard",
                50.0,
                1.2,
                10.0));
        products.add(new DigitalProduct(2L,
                "E-Book",
                15.0,
                "http://download.com/book",
                5.5));
    }

    public List<Product> all() {
        return products;
    }

    public Product find(Long id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }return null;
    }

    public boolean exists(Long id) {
        return find(id) != null;
    }

    public void save(Product product) {
        products.add(product);
    }

    public boolean delete(Long id) {
        Product p = find(id);
        if (p != null) {
            return products.remove(p);
        }
        return false;
    }
}
