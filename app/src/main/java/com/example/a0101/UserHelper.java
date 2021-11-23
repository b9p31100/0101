package com.example.a0101;

public class UserHelper {
    String religion,principle,ucustom,allergy,uid;

    public UserHelper(){
    }

    public UserHelper(String religion, String principle, String ucustom, String allergy, String uid) {
        this.religion = religion;
        this.principle = principle;
        this.ucustom = ucustom;
        this.allergy = allergy;
        this.uid = uid;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getUcustom() {
        return ucustom;
    }

    public void setUcustom(String ucustom) {
        this.ucustom = ucustom;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
