package com.song.blog.dto;

import java.util.HashMap;
import java.util.Map;

public class Product {
    //订单号
    private String orderNO;

    //产品序号
    private String productId;

    //买的数量
    private String totalCount;

    //邮箱
    private String email;

    //联系方式
    private String contact;

    //支付方式
    private String paymentType;

    //支付方式
    private String userPaymentId;

    private String submitButton;

    public Product() {
        this.submitButton = "btnPayOrder";
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getUserPaymentId() {
        return userPaymentId;
    }

    public void setUserPaymentId(String userPaymentId) {
        this.userPaymentId = userPaymentId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "orderNO='" + orderNO + '\'' +
                ", productId='" + productId + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", userPaymentId='" + userPaymentId + '\'' +
                ", submitButton='" + submitButton + '\'' +
                '}';
    }

    public Map toMap(){
        Map<String, String> map = new HashMap<>(8);
        map.clear();
        map.put("orderNO",orderNO);
        map.put("productId",productId);
        map.put("totalCount",totalCount);
        map.put("email",email);
        map.put("contact",contact);
        map.put("paymentType",paymentType);
        map.put("userPaymentId",userPaymentId);
        map.put("submitButton",submitButton);
        return map;
    }
}
