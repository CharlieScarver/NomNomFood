package com.example.nomnomfood.Model;

import java.util.List;

public class OrderRequest {
    private String phone;
    private String name;
    private String address;
    private String total;
    private List<Order> cartList; // list of ordered food

    public OrderRequest() {
    }

    public OrderRequest(String phone, String name, String address, String total, List<Order> cartList) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.cartList = cartList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getCartList() {
        return cartList;
    }

    public void setCartList(List<Order> cartList) {
        this.cartList = cartList;
    }
}
