package com.tab3e.model;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class InfractionDocItem {
    private String dayName;
    private String infractionDegree;
    private String dateMeladi;
    private String dateHegri;
    private String time;
    private String cause;

    public InfractionDocItem(String dayName, String infractionDegree, String dateMeladi, String dateHegri, String time, String cause) {
        this.dayName = dayName;
        this.infractionDegree = infractionDegree;
        this.dateMeladi = dateMeladi;
        this.dateHegri = dateHegri;
        this.time = time;
        this.cause = cause;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getInfractionDegree() {
        return infractionDegree;
    }

    public void setInfractionDegree(String infractionDegree) {
        this.infractionDegree = infractionDegree;
    }

    public String getDateMeladi() {
        return dateMeladi;
    }

    public void setDateMeladi(String dateMeladi) {
        this.dateMeladi = dateMeladi;
    }

    public String getDateHegri() {
        return dateHegri;
    }

    public void setDateHegri(String dateHegri) {
        this.dateHegri = dateHegri;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
