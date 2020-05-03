package com.example.mv_medic;

public class data {
    private String Phonenumber;
    private String PaymentId;
    private String Price;
    private String Date;


    public data(String phonenumber, String paymentId, String price, String date) {
        Phonenumber = phonenumber;
        PaymentId = paymentId;
        Price = price;
        Date = date;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public data() {

    }



}
