package controller;

import model.Product;
import model.PhysicalProduct;
import model.DigitalProduct;
import service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    // Constructor Injection: Spring automatically plugs ProductService in here!
    public ProductController(ProductService service) {
        this.service = service;
    }

    // GET http://localhost:8080/products
    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    // GET http://localhost:8080/products/1
    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    // GET http://localhost:8080/products/summaries
    @GetMapping("/summaries")
    public List<String> getSummaries() {
        return service.getAllSummaries();
    }

    // GET http://localhost:8080/products/budget?limit=60.0
    @GetMapping("/budget")
    public List<Product> selectWithinBudget(@RequestParam("limit") double limit) {
        return service.selectWithinBudget(limit);
    }

    // POST http://localhost:8080/products/digital (Returns 201 Created)
    @PostMapping("/digital")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addDigital(@RequestBody DigitalProduct product) {
        return service.add(product);
    }

    // POST http://localhost:8080/products/physical (Returns 201 Created)
    @PostMapping("/physical")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addPhysical(@RequestBody PhysicalProduct product) {
        return service.add(product);
    }

    // POST http://localhost:8080/products/physical (Returns 201 Created)
    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable("id") Long id) {
        return service.delete(id);
    }

}
