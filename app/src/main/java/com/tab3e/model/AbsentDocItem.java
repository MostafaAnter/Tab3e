package com.tab3e.model;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class AbsentDocItem {
    private String ID;
    private String day;
    private String m_date;
    private String h_date;
    private String hour1;
    private String hour2;
    private String typeabsent;
    private String typewithe;

    public AbsentDocItem(String ID, String day, String m_date, String h_date, String hour1, String hour2, String typeabsent, String typewithe) {
        this.ID = ID;
        this.day = day;
        this.m_date = m_date;
        this.h_date = h_date;
        this.hour1 = hour1;
        this.hour2 = hour2;
        this.typeabsent = typeabsent;
        this.typewithe = typewithe;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getM_date() {
        return m_date;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }

    public String getH_date() {
        return h_date;
    }

    public void setH_date(String h_date) {
        this.h_date = h_date;
    }

    public String getHour1() {
        return hour1;
    }

    public void setHour1(String hour1) {
        this.hour1 = hour1;
    }

    public String getHour2() {
        return hour2;
    }

    public void setHour2(String hour2) {
        this.hour2 = hour2;
    }

    public String getTypeabsent() {
        return typeabsent;
    }

    public void setTypeabsent(String typeabsent) {
        this.typeabsent = typeabsent;
    }

    public String getTypewithe() {
        return typewithe;
    }

    public void setTypewithe(String typewithe) {
        this.typewithe = typewithe;
    }
}
