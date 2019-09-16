package com.example.haoss.ui.person.integral;

public class IntegralRecord {


    /**
     * date : 2019-08-08 12:10:10
     * storeName : 月饼
     * image :
     * count : 1
     * integralCount : 1000
     */

    private String date;
    private String storeName;
    private String image;
    private int count;
    private String integralCount;

    public IntegralRecord() {
    }

    public IntegralRecord(String date, String storeName, String image, int count, String integralCount) {
        this.date = date;
        this.storeName = storeName;
        this.image = image;
        this.count = count;
        this.integralCount = integralCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIntegralCount() {
        return integralCount;
    }

    public void setIntegralCount(String integralCount) {
        this.integralCount = integralCount;
    }
}
