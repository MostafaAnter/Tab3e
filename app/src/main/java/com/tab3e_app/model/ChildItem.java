package com.tab3e_app.model;

/**
 * Created by mostafa_anter on 3/23/17.
 */

public class ChildItem {
    private String ID;
    private String id_father;
    private String id_son;
    private String id_school;

    public ChildItem(String ID, String id_father, String id_son, String id_school) {
        this.ID = ID;
        this.id_father = id_father;
        this.id_son = id_son;
        this.id_school = id_school;
    }

    public ChildItem() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getId_father() {
        return id_father;
    }

    public void setId_father(String id_father) {
        this.id_father = id_father;
    }

    public String getId_son() {
        return id_son;
    }

    public void setId_son(String id_son) {
        this.id_son = id_son;
    }

    public String getId_school() {
        return id_school;
    }

    public void setId_school(String id_school) {
        this.id_school = id_school;
    }
}
