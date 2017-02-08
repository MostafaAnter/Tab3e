package com.tab3e_app.model;

/**
 * Created by mostafa_anter on 1/12/17.
 */

public class ContactItem {
    private String ID;
    private String job_name;
    private String name;
    private String mobile;
    private String email;
    private String other;

    public ContactItem(String ID, String job_name, String name, String mobile, String email, String other) {
        this.ID = ID;
        this.job_name = job_name;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.other = other;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
