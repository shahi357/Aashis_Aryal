package com.tanka.auctionapp.Model;

public class User {
    private String fullname;
    private String address;
    private String mobile_number;
    private String email;
    private String password;
    private String profile;



    public User(String fullname, String address, String mobile_number, String email, String password, String profile){
        this.fullname = fullname;
        this.address = address;
        this.mobile_number = mobile_number;
        this.email = email;
        this.password = password;
        this.profile = profile;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile(){
        return profile;
    }

    public void setProfile(String profile){
        this.profile = profile;
    }
}
