package com.example.spacebottomview.pojo_classes;

import com.google.gson.annotations.SerializedName;

public class Brand {
    public  String product_id;
    public  String product_name;
    public String product_price;
    public String product_image;

    public String spec_items;

    public String product_rate;
    public String delivery_date;
    public String delivery_cost;
    public String online_price_rate;
    public String website_name;
    public String logo;
    public String prod_url;
    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String brand_name;
}
