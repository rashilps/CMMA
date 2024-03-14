package com.example.cmma;

public class AddNewMaterialPojo {
//    private String uid;
    private String materialName;
    private String materialDescription;
    private String currentQuantity;
    private String supplierName;
    private String supplierAddress;
    private String supplierContact;
    private String supplierEmail;
    public AddNewMaterialPojo() {}

    public AddNewMaterialPojo(String materialName, String materialDescription, String currentQuantity, String suppluerName, String supplierAddress, String supplierContact,String supplierEmail) {
//        this.uid = uid;
        this.materialName = materialName;
        this.materialDescription = materialDescription;
        this.currentQuantity = currentQuantity;
        this.supplierName = suppluerName;
        this.supplierAddress = supplierAddress;
        this.supplierContact = supplierContact;
        this.supplierEmail = supplierEmail;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(String currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
}
