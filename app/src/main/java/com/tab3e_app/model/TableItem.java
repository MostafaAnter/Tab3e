package com.tab3e_app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mostafa_anter on 1/12/17.
 */

public class TableItem implements Parcelable {
    private String ID;
    private String id_school;
    private String id_table;
    private String day;
    private String firest;
    private String second;
    private String therd;
    private String free;
    private String fourth;
    private String fifth;
    private String sixth;
    private String seventh;
    private String year;
    private String term;
    private String level;
    private String row;
    private String section;


    public TableItem(String ID, String id_school, String id_table, String day, String firest, String second, String therd, String free, String fourth, String fifth, String sixth, String seventh, String year, String term, String level, String row, String section) {
        this.ID = ID;
        this.id_school = id_school;
        this.id_table = id_table;
        this.day = day;
        this.firest = firest;
        this.second = second;
        this.therd = therd;
        this.free = free;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
        this.year = year;
        this.term = term;
        this.level = level;
        this.row = row;
        this.section = section;
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

    public String getId_table() {
        return id_table;
    }

    public void setId_table(String id_table) {
        this.id_table = id_table;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFirest() {
        return firest;
    }

    public void setFirest(String firest) {
        this.firest = firest;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getTherd() {
        return therd;
    }

    public void setTherd(String therd) {
        this.therd = therd;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
    }

    public String getFifth() {
        return fifth;
    }

    public void setFifth(String fifth) {
        this.fifth = fifth;
    }

    public String getSixth() {
        return sixth;
    }

    public void setSixth(String sixth) {
        this.sixth = sixth;
    }

    public String getSeventh() {
        return seventh;
    }

    public void setSeventh(String seventh) {
        this.seventh = seventh;
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

    protected TableItem(Parcel in) {
        ID = in.readString();
        id_school = in.readString();
        id_table = in.readString();
        day = in.readString();
        firest = in.readString();
        second = in.readString();
        therd = in.readString();
        free = in.readString();
        fourth = in.readString();
        fifth = in.readString();
        sixth = in.readString();
        seventh = in.readString();
        year = in.readString();
        term = in.readString();
        level = in.readString();
        row = in.readString();
        section = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(id_school);
        dest.writeString(id_table);
        dest.writeString(day);
        dest.writeString(firest);
        dest.writeString(second);
        dest.writeString(therd);
        dest.writeString(free);
        dest.writeString(fourth);
        dest.writeString(fifth);
        dest.writeString(sixth);
        dest.writeString(seventh);
        dest.writeString(year);
        dest.writeString(term);
        dest.writeString(level);
        dest.writeString(row);
        dest.writeString(section);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TableItem> CREATOR = new Parcelable.Creator<TableItem>() {
        @Override
        public TableItem createFromParcel(Parcel in) {
            return new TableItem(in);
        }

        @Override
        public TableItem[] newArray(int size) {
            return new TableItem[size];
        }
    };
}