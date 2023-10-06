package Clases;

public class Product {
    private String code, name;
    private double price;
    private int amount;

    public Product(String code, String name, double price, int amount) {
        if (price < 0 || amount < 0) {
            throw new IllegalArgumentException("Price and quantity cannot be negative.");
        }
        this.code = code;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
