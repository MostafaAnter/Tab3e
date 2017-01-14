package com.tab3e.model;

/**
 * Created by mostafa_anter on 1/11/17.
 */

public class StudentData {
    private String ID;
    private String id_school;
    private String name;
    private String mobile;
    private String email;
    private String id_card;
    private String year;
    private String term;
    private String level;
    private String row;
    private String section;
    private String country;
    private String b_date;

    public String getH_date() {
        return h_date;
    }

    public void setH_date(String h_date) {
        this.h_date = h_date;
    }

    private String h_date;

    public StudentData(String ID, String id_school, String name, String mobile,
                       String email, String id_card, String year, String term,
                       String level, String row, String section, String country,
                       String b_Date, String h_date) {
        this.ID = ID;
        this.id_school = id_school;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.id_card = id_card;
        this.year = year;
        this.term = term;
        this.level = level;
        this.row = row;
        this.section = section;
        this.country = country;
        this.b_date = b_Date;
        this.h_date = h_date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getId_school() {
        return id_school;
    }

    public void setId_school(String id_school) {
        this.id_school = id_school;
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

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getB_Date() {
        return b_date;
    }

    public void setB_Date(String b_Date) {
        this.b_date = b_Date;
    }
}
