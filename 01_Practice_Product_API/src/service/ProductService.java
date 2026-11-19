package service;

import model.Product;
import model.DigitalProduct;
import model.PhysicalProduct;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import exception.ProductNotFoundException;
import repository.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    //Methods
    public List<Product> getAll() {
        return repo.all();
    }

    public Product getById(Long id) {
        Product p = repo.find(id);
        if (p == null) {
            throw new ProductNotFoundException(id);
        }return p;
    }

    public Product add(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }if (repo.exists(product.getId())) {
            throw new IllegalArgumentException("Product with ID already exists");
        }
        repo.save(product);
        return product;
    }

    public boolean delete(Long id) {
        return repo.delete(id);
    }

    public double calculateTotalValue() {
        double total = 0;
        for (Product p : repo.all()) {
            total += p.getPrice();
        }
        return total;
    }

    public List<String> getAllSummaries() {
        List<String> summaries = new ArrayList<>();
        for (Product p : repo.all()) {
            summaries.add(p.getSummary());
        }
        return summaries;
    }

    public List<Product> selectWithinBudget(double budget) {
        if (budget <= 0) {
            throw new IllegalArgumentException("Budget must be greater than zero");
        }

        // Copy all products into a new list
        List<Product> sorted = new ArrayList<>(repo.all());

        // Sort using our separate helper method below
        sorted.sort(this::compareProducts);

        // Greedy selection: pick products that fit the remaining budget
        List<Product> selected = new ArrayList<>();
        double used = 0;

        for (Product p : sorted) {
            if (p.getPrice() <= budget - used) {
                selected.add(p);
                used += p.getPrice();
            }
        }
        return selected;
    }

    // Separate helper method: compares two products
    private int compareProducts(Product first, Product second) {
        int result = Double.compare(first.getPrice(), second.getPrice());
        if (result != 0) {
            return result;
        }
        return Long.compare(first.getId(), second.getId());
    }

      // Can Also be used
//    Collections.sort(sorted, new Comparator<Product>() {
//        @Override
//        public int compare(Product first, Product second) {
//            int result = Double.compare(first.getPrice(), second.getPrice());
//            if (result != 0) {
//                return result;
//            }
//            return Long.compare(first.getId(), second.getId());
//        }
//    });

    // Can also be used
//    sorted.sort(new Comparator<Product>() {
//        @Override
//        public int compare(Product first, Product second) {
//            int result = Double.compare(first.getPrice(), second.getPrice());
//            if (result != 0) {
//                return result;
//            }
//            return Long.compare(first.getId(), second.getId());
//        }
//    });

    // Also Works
//    sorted.sort(
//            Comparator.comparingDouble(Product::getPrice)
//            .thenComparingLong(Product::getId)
//    );

}
