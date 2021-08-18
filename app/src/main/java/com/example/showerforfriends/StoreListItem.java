package com.example.showerforfriends;

public class StoreListItem {
    private String storeName;
    private String storeIntro;
    private String storeLocation;
    private String storeURL;
    private double position1, position2;
    private int storePicture;
    private boolean storeBookmark;

    StoreListItem(String storeName, String storeIntro, String storeLocation, String storeURL, int storePicture, boolean storeBookmark, double position1, double position2)
    {
        this.storeName = storeName;
        this.storeIntro = storeIntro;
        this.storeLocation = storeLocation;
        this.storeURL = storeURL;
        this.storePicture = storePicture;
        this.storeBookmark = storeBookmark;
        this.position1 = position1;
        this.position2 = position2;
    }

    public String getStoreName() {
        return storeName;
    }
    public String getStoreIntro() {
        return storeIntro;
    }
    public String getStoreLocation() {
        return storeLocation;
    }
    public String getStoreURL() {
        return storeURL;
    }
    public int getStorePicture() {
        return storePicture;
    }
    public boolean getStoreBookmark() {
        return storeBookmark;
    }
    public double getPosition1() {
        return position1;
    }
    public double getPosition2() {
        return position2;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public void setStoreIntro(String storeIntro) {
        this.storeIntro = storeIntro;
    }
    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }
    public void setStoreURL(String storeURL) {
        this.storeURL = storeURL;
    }
    public void setStorePicture(int storePicture) {
        this.storePicture = storePicture;
    }
    public void setStoreBookmark(boolean storeBookmark) {
        this.storeBookmark = storeBookmark;
    }
    public void setPosition1(double position1) {
        this.position1 = position1;
    }
    public void setPosition2(double position2) {
        this.position2 = position2;
    }
}
