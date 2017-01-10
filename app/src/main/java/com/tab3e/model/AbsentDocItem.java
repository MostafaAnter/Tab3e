package com.tab3e.model;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class AbsentDocItem {
    private String dayName;
    private String dayDetail;
    private String dateMeladi;
    private String dateHegri;
    private String time;
    private String isCause;

    public AbsentDocItem(String dayName, String dayDetail, String dateMeladi, String dateHegri, String time, String isCause) {
        this.dayName = dayName;
        this.dayDetail = dayDetail;
        this.dateMeladi = dateMeladi;
        this.dateHegri = dateHegri;
        this.time = time;
        this.isCause = isCause;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayDetail() {
        return dayDetail;
    }

    public void setDayDetail(String dayDetail) {
        this.dayDetail = dayDetail;
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

    public String getIsCause() {
        return isCause;
    }

    public void setIsCause(String isCause) {
        this.isCause = isCause;
    }
}
