package com.example.applibrary.entity;

import java.util.List;

public class ShopCart {
    private List<CartInfo> valid;
    private List invalid;

    public List<CartInfo> getValid() {
        return valid;
    }

    public void setValid(List<CartInfo> valid) {
        this.valid = valid;
    }

    public List getInvalid() {
        return invalid;
    }

    public void setInvalid(List invalid) {
        this.invalid = invalid;
    }
}
