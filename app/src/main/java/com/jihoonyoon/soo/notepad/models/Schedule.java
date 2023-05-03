package com.jihoonyoon.soo.notepad.models;

import com.jihoonyoon.soo.notepad.database.AppDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;

@Table(database = AppDatabase.class, allFields = true)
public class Schedule extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int id;
    private Calendar calendar;
    private String text;
    private int backgroundColor;
    private int textColor;

    public Schedule() {}

    public Schedule(int id, Calendar calendar, String text, int backgroundColor, int textColor) {
        this.id = id;
        this.calendar = calendar;
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        return id == schedule.id;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", calendar=" + calendar +
                ", text='" + text + '\'' +
                ", backgroundColor=" + backgroundColor +
                ", textColor=" + textColor +
                '}';
    }
}
