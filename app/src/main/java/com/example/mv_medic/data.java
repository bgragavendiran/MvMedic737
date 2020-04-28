package com.example.mv_medic;

public class data {
    private String name;
    private String invoiceno;
    private String price;
    private String date;


    public data(String name, String invoiceno, String price, String date) {
        this.name = name;
        this.invoiceno = invoiceno;
        this.price = price;
        this.date = date;
    }

    public data() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
