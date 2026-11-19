package model;

public abstract class Product {
    private Long id;
    private String name;
    private double price;

    public Product() {};

    public abstract String getSummary();

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        setPrice(price);
    }

    //Getters
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public double getPrice() {
        return this.price;
    }

    //Setters
    public void setId(Long newId) {
        this.id = newId;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public void setPrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        this.price = newPrice;
    }

}
