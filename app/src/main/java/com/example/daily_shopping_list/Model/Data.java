package com.example.daily_shopping_list.Model;

public class Data {

    String type;
    String note;
    String id;
    int amount;
    String  date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Data(String type, String note, String id, int amount, String date) {
        this.type = type;
        this.note = note;
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public Data(){

    }
}
