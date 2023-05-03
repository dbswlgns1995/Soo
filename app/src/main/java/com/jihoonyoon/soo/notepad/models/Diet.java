package com.jihoonyoon.soo.notepad.models;

import androidx.annotation.Nullable;

import com.jihoonyoon.soo.notepad.database.AppDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;

@Table(database = AppDatabase.class, allFields = true)
public class Diet extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int id;
    private Calendar calendar;
    private String menu1;
    private String menu2;
    private String menu3;
    private int backgroundColor;
    private int textColor;

    public Diet(){}

    public Diet(int id, Calendar calendar, String menu1, String menu2, String menu3, int backgroundColor, int textColor) {
        this.id = id;
        this.calendar = calendar;
        this.menu1 = menu1;
        this.menu2 = menu2;
        this.menu3 = menu3;
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

    public String getMenu1() {
        return menu1;
    }

    public void setMenu1(String menu1) {
        this.menu1 = menu1;
    }

    public String getMenu2() {
        return menu2;
    }

    public void setMenu2(String menu2) {
        this.menu2 = menu2;
    }

    public String getMenu3() {
        return menu3;
    }

    public void setMenu3(String menu3) {
        this.menu3 = menu3;
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

        Diet diet = (Diet) o;

        return id == diet.id;
    }

    @Override
    public String toString() {
        return "Diet{" +
                "id=" + id +
                ", calendar=" + calendar +
                ", menu1='" + menu1 + '\'' +
                ", menu2='" + menu2 + '\'' +
                ", menu3='" + menu3 + '\'' +
                ", backgroundColor=" + backgroundColor +
                ", textColor=" + textColor +
                '}';
    }
}