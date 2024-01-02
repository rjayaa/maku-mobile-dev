package com.project.maku_mobile_based.model;

public class Tenants {

    String tenantsCategory, tenantsDescription,tenantsName, tenantsUrlImage;

    public Tenants(){

    }

    public Tenants(String tenantsCategory, String tenantsDescription, String tenantsName, String tenantsUrlImage) {
        this.tenantsCategory = tenantsCategory;
        this.tenantsDescription = tenantsDescription;
        this.tenantsName = tenantsName;
        this.tenantsUrlImage = tenantsUrlImage;
    }

    public String getTenantsCategory() {
        return tenantsCategory;
    }

    public void setTenantsCategory(String tenantsCategory) {
        this.tenantsCategory = tenantsCategory;
    }

    public String getTenantsDescription() {
        return tenantsDescription;
    }

    public void setTenantsDescription(String tenantsDescription) {
        this.tenantsDescription = tenantsDescription;
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
