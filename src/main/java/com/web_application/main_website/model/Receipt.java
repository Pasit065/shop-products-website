package com.web_application.main_website.model;

public class Receipt {
    private String clientId;
    private String productName;
    private Float price; 
    private String receiptTime;

    public Receipt(ClientForm clientForm, String productName, Float price) {
        this.clientId = clientForm.getClientId();
        this.productName = productName;
        this.price = price; 
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getProductName() {
        return this.productName;
    }

    public Float getPrice() {
        return this.price;
    }

    public String getReceiptTime() {
        return this.receiptTime;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public void showReceiptAllDetails(ClientForm clientForm) {
        String receiptDetail = "------ Thanks you for shopping ------" + 
                                "\n\nProduct: " + this.productName + 
                                "\nTotal: " + this.price +
                                "\nFrom " + this.clientId + 
                                "\n" + clientForm.getName() + " " + clientForm.getSurname() +
                                "\nstatus: " + clientForm.getStatus() +
                                "\nTotal payment" + clientForm.getTotalPayment();

        System.out.println(receiptDetail);

    }
}

