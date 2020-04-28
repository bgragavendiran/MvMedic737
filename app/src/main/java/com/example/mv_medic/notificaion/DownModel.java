package com.example.mv_medic.notificaion;

public class DownModel {

    private String title, pdfurl;


    public DownModel(String title, String pdfurl) {
        this.title = title;
        this.pdfurl = pdfurl;
    }

    public DownModel() {
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
