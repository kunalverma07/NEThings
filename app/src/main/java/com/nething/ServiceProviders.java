package com.nething;

public class ServiceProviders {

    String name, address, phone, email, pincode, locality, image ,charging_fee, service_type,uid,district,date,time, url;

    public ServiceProviders() {
    }

    public ServiceProviders(String name, String address, String phone, String email, String pincode, String locality, String image, String charging_fee, String service_type, String uid, String district, String date, String time, String url) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.pincode = pincode;
        this.locality = locality;
        this.image = image;
        this.charging_fee = charging_fee;
        this.service_type = service_type;
        this.uid = uid;
        this.district = district;
        this.date = date;
        this.time = time;
        this.url = url;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLocality() {
        return locality;
    }


    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCharging_fee() {
        return charging_fee;
    }

    public void setCharging_fee(String charging_fee) {
        this.charging_fee = charging_fee;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
