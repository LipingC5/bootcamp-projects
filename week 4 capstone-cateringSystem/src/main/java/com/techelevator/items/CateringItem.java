package com.techelevator.items;
/*
    This represents a single catering item in the system
 */
public class CateringItem {

    private String productName;
    private double productPrice;
    private String productType;
    private String productCode;
    private int productQuantity = 25;

    public CateringItem(){
    }

    public CateringItem(String productName) {
        this.productName = productName;
        this.productPrice = getProductPrice();
        this.productCode = getProductCode();
        this.productType = getProductType();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int quantity) {
        this.productQuantity -= quantity;
    }

    public String isItemAvailable() {
        if (getProductQuantity() == 25) {
            return "25";
        }
        return "SOLD OUT";
    }
}
