package com.project.maku_mobile_based.model;

public class Tenants {

    String tenantsCategory, tenantsName, tenantsUrlImage;


    public Tenants(){

    }
    public Tenants(String tenantsCategory, String tenantsName, String tenantsUrlImage) {
        this.tenantsCategory = tenantsCategory;
        this.tenantsName = tenantsName;
        this.tenantsUrlImage = tenantsUrlImage;
    }

    public String getTenantsCategory() {
        return tenantsCategory;
    }

    public void setTenantsCategory(String tenantsCategory) {
        this.tenantsCategory = tenantsCategory;
    }

    public String getTenantsName() {
        return tenantsName;
    }

    public void setTenantsName(String tenantsName) {
        this.tenantsName = tenantsName;
    }

    public String getTenantsUrlImage() {
        return tenantsUrlImage;
    }

    public void setTenantsUrlImage(String tenantsUrlImage) {
        this.tenantsUrlImage = tenantsUrlImage;
    }
}
