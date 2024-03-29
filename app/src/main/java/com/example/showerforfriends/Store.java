package com.example.showerforfriends;

public class Store {
    int store_id;
    String store_name;
    String store_info;
    String store_location;
    String store_uri;
    double store_pos1;
    double store_pos2;
    int store_img;

    public Store(int store_id, String store_name, String store_info,  String store_location, String store_uri, double store_pos1, double store_pos2, int store_img)
    {
        this.store_id = store_id;
        this.store_name = store_name;
        this.store_info = store_info;
        this.store_location = store_location;
        this.store_uri = store_uri;
        this.store_pos1 = store_pos1;
        this.store_pos2 = store_pos2;
        this.store_img = store_img;
    }

    public int getStore_id() {
        return store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_info() {
        return store_info;
    }

    public String getStore_location() {
        return store_location;
    }

    public String getStore_uri() {
        return store_uri;
    }

    public double getStore_pos1() {
        return store_pos1;
    }

    public double getStore_pos2() {
        return store_pos2;
    }

    public int getStore_img() {
        return store_img;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setStore_info(String store_info) {
        this.store_info = store_info;
    }

    public void setStore_location(String store_location) {
        this.store_location = store_location;
    }

    public void setStore_uri(String store_uri) {
        this.store_uri = store_uri;
    }

    public void setStore_pos1(double store_pos1) {
        this.store_pos1 = store_pos1;
    }

    public void setStore_pos2(double store_pos2) {
        this.store_pos2 = store_pos2;
    }

    public void setStore_img(int store_img) {
        this.store_img = store_img;
    }
}
