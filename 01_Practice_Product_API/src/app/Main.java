package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"controller",
        "service",
        "repository",
        "exception",
        "model"})

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}























//import model.DigitalProduct;
//import model.PhysicalProduct;
//import model.Product;
//import service.ProductService;
//import exception.ProductNotFoundException;
//import repository.ProductRepository;
//
//import java.util.List;
//
//public class app.Main {
//    public static void main(String[] args) {
//        ProductRepository repo = new ProductRepository();
//        ProductService service = new ProductService(repo);
//
//        List<Product> products = service.getAll();
//
//        System.out.println("==== All Products ====");
//        int totalProducts = products.size();
//        System.out.println("Total Products: " + totalProducts + "\n");
//
//
//        System.out.println("==== Product Details ====");
//        for (Product p : products) {
//            System.out.println("ID: " + p.getId());
//            System.out.println("Name: " + p.getName());
//            System.out.println("Price: " + p.getPrice() + "\n");
//        }
//
//        System.out.println("=== Find product by ID : ID = 1 ====");
//        Product p = service.getById(1L);
//        System.out.println("ID: " + p.getId());
//        System.out.println("Name: " + p.getName());
//        System.out.println("Price: " + p.getPrice());
//
//        System.out.println("\n-------------------------------------------");
//
//        try {
//            Product p1 = service.getById(99L);
//            System.out.println("Found: " + p1.getName());
//            System.out.println("ID: " + p1.getId());
//            System.out.println("Name: " + p1.getName());
//            System.out.println("Price: " + p1.getPrice());
//        }catch (ProductNotFoundException e) {
//            System.out.println("\nCaught custom exception: " + e.getMessage());
//        }
//
//        double totalValue = service.calculateTotalValue();
//        System.out.println("\nTotal value is: " + totalValue);
//
//        service.add(new DigitalProduct(3L, "Music Album", 20.0, "http://...", 120.0));
//        double totalValueNew = service.calculateTotalValue();
//        System.out.println("New Total value: " + totalValueNew);
//
//        try {
//            service.add(new PhysicalProduct(1L, "Game Disc", 77.00, 0.30,210.00));
//        }catch (IllegalArgumentException e) {
//            System.out.println("\nID Already Exists");
//        }
//
//        service.delete(2L);
//        System.out.println("\nRemaining Items: " + products.size() + "\n");
//
//        System.out.println(service.getAllSummaries());
//
//        System.out.println("\n==== Budget Planner (Budget: £60.0) ====");
//        List<Product> affordable = service.selectWithinBudget(60.0);
//        for (Product item : affordable) {
//            System.out.println("Selected -> ID: " + item.getId() + ", Name: " + item.getName() + ", Price: £" + item.getPrice());
//        }
//    }
//}
