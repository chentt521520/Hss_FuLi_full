package com.example.haoss.ui.person.integral;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

//积分数据
public class IntegralInfo {


    /**
     * categoryId : 1
     * categoryName : 猜你喜欢
     * list : [{"productId":1,"image":"","storeName":"红酒","integralCount":"1000","sales":"100"},{"productId":1,"image":"","storeName":"抽纸三包装","integralCount":"500","sales":"100"}]
     */

    private int categoryId;
    private String categoryName;
    private List<ListBean> list;

    public IntegralInfo(int categoryId, String categoryName, List<ListBean> list) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.list = list;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * productId : 1
         * image :
         * storeName : 红酒
         * integralCount : 1000
         * sales : 100
         */

        private int productId;
        private String image;
        private String storeName;
        private String integralCount;
        private String sales;

        protected ListBean() {
        }

        public ListBean(int productId, String image, String storeName, String integralCount, String sales) {
            this.productId = productId;
            this.image = image;
            this.storeName = storeName;
            this.integralCount = integralCount;
            this.sales = sales;
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                ListBean bean = new ListBean();
                bean.storeName = in.readString();
                bean.productId = in.readInt();
                bean.image = in.readString();
                bean.integralCount = in.readString();
                bean.sales = in.readString();
                return bean;
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getIntegralCount() {
            return integralCount;
        }

        public void setIntegralCount(String integralCount) {
            this.integralCount = integralCount;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.productId);
            dest.writeString(this.storeName);
            dest.writeString(this.image);
            dest.writeString(this.integralCount);
            dest.writeString(this.sales);
        }

    }
}
