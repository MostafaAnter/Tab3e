package com.tab3e_app.model;

/**
 * Created by mostafa_anter on 1/6/17.
 */

public class SpinnerModel {
    private String label;
    private String id;
    private String absent;
    private String errors;

    public SpinnerModel(String label, String id) {
        this.label = label;
        this.id = id;
    }


    public SpinnerModel(String label, String id, String absent, String errors) {
        this.label = label;
        this.id = id;
        this.absent = absent;
        this.errors = errors;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
