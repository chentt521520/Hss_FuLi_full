package com.example.applibrary.entity;

public class OrderCount {
    /**
     * order_count : 30
     * payment_count : 6
     * sum_price : 90.22
     * unpaid_count : 24
     * unshipped_count : 0
     * complete_count : 0
     */

    private int order_count;
    private int payment_count;
    private double sum_price;
    private int unpaid_count;
    private int unshipped_count;
    private int complete_count;

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public int getPayment_count() {
        return payment_count;
    }

    public void setPayment_count(int payment_count) {
        this.payment_count = payment_count;
    }

    public double getSum_price() {
        return sum_price;
    }

    public void setSum_price(double sum_price) {
        this.sum_price = sum_price;
    }

    public int getUnpaid_count() {
        return unpaid_count;
    }

    public void setUnpaid_count(int unpaid_count) {
        this.unpaid_count = unpaid_count;
    }

    public int getUnshipped_count() {
        return unshipped_count;
    }

    public void setUnshipped_count(int unshipped_count) {
        this.unshipped_count = unshipped_count;
    }

    public int getComplete_count() {
        return complete_count;
    }

    public void setComplete_count(int complete_count) {
        this.complete_count = complete_count;
    }

}
