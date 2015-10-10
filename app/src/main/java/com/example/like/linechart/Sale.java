package com.example.like.linechart;

/**
 * Created by like on 2015/10/10.
 */
public class Sale {

    private int id;
    private String date;
    private int saleNum;

    public Sale(int id, String date, int saleNum) {
        this.date = date;
        this.id = id;
        this.saleNum = saleNum;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getSaleNum() {
        return saleNum;
    }
}
