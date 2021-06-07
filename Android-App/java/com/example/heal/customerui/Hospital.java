package com.example.heal.customerui;

public class Hospital {

    private String name;
    private String address;
    private String phone;

    public Hospital(String name,String address,String phone) {

        this.address=address;
        this.name=name;
        this.phone=phone;

    }

    public String getName(){
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
