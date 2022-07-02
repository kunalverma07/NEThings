package com.nething;

public class ServiceProviders {

    String name, designation, locality, image;

    public ServiceProviders(){

    }

    public ServiceProviders(String name, String designation, String locality, String image) {
        this.name = name;
        this.designation = designation;
        this.locality = locality;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
}
