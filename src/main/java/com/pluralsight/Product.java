package com.pluralsight;

public class Product {
    private String id;
    private String name;
    private double price;
    private int cartQuantity;

    public Product(String id, String name, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
        cartQuantity = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void incrementCartQuantity(){
        ++this.cartQuantity;
    }

    @Override
    public String toString() {
        return String.format(id + ", " + name + ", " + "$%.2f", price);
    }

    public String toStringCart() {
        double lineTotal = price * cartQuantity;
        return String.format(cartQuantity + ", " + id + ", " + name + ", " + "$%.2f", lineTotal);
    }
}