
package com.web_application.main_website.model;

public class ClientForm {
    private String productNameBuy;
    private String lastestUpdated;
    private String clientId;
    private String name;
    private String surname;
    private String status;
    private Float totalPayment;

    public String getClientId() {
        return this.clientId;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getStatus() {
        return this.status;
    }

    public Float getTotalPayment() {
        return this.totalPayment;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalPayment(Float totalPayment) {
        this.totalPayment = totalPayment;
    }

    public void setStatusRefernceFromPayment() {
        this.status = this.totalPayment >= 2000 ? "Premium": 
                      this.totalPayment >= 1000 ? "VIP":
                      "Standard";
    }

    public void updateNewTotalPaymentFromNewReceipt(Receipt receipt) {
        Float newProductBuyPrice = receipt.getPrice();
        this.totalPayment += newProductBuyPrice;
    }

    public void showClientDetail() {
        String displayText = "\nThis is: " + 
                                this.name + " " + 
                                this.surname +"\ncli id:" + 
                                this.clientId + "\nWith total payment: " + 
                                this.totalPayment + " Baht\nstatus: " 
                                + this.status + "\n";

        System.out.println(displayText);
    }

    public String getProductNameBuy() {
        return this.productNameBuy;
    }

    public String getLastestUpdated() {
        return this.lastestUpdated;
    }

    public void setProductNameBuy(String productNameBuy) {
        this.productNameBuy = productNameBuy;
    }

    public void setLastestUpdated(String lastestUpdated) {
        this.lastestUpdated = lastestUpdated;
    }

}
