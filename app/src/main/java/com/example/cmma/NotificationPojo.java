package com.example.cmma;

public class NotificationPojo {
    private String materialName;
    private String currentQuantity;

    public NotificationPojo(String materialName, String currentQuantity) {
//        this.uid = uid;
        this.materialName = materialName;
        this.currentQuantity = currentQuantity;
    }
    public NotificationPojo() {}

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(String currentQuantity) {
        this.currentQuantity = currentQuantity;
    }



}
