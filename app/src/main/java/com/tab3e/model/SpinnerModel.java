package com.tab3e.model;

/**
 * Created by mostafa_anter on 1/6/17.
 */

public class SpinnerModel {
    private String label;
    private String id;

    public SpinnerModel(String label, String id) {
        this.label = label;
        this.id = id;
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
}
