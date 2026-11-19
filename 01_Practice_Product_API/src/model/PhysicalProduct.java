package model;

public class PhysicalProduct extends Product{
    private double weightKg;
    private double shippingCost;

    public PhysicalProduct() {};

    @Override
    public String getSummary() {
        return "Physical: " + getName() + " [" + weightKg + "kg] (Shipping: £" + shippingCost + ")";
    }

    public PhysicalProduct(Long id,
                           String name,
                           double price,
                           double weightKg,
                           double shippingCost) {
        super(id, name, price);
        setWeightKg(weightKg);
        setShippingCost(shippingCost);
    }

    //Getters
    public double getWeightKg() {
        return weightKg;
    }
    public double getShippingCost() {
        return shippingCost;
    }

    //Setters
    public void setWeightKg(double newWeightKg) {
        if (newWeightKg < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.weightKg = newWeightKg;
    }
    public void setShippingCost(double newShippingCost) {
        if (newShippingCost < 0) {
            throw new IllegalArgumentException("Shipping cost cannot be negative");
        }
        this.shippingCost = newShippingCost;
    }
}
