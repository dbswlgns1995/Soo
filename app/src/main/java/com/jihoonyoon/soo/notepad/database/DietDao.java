package com.jihoonyoon.soo.notepad.database;

import com.jihoonyoon.soo.notepad.models.Diet;
import com.jihoonyoon.soo.notepad.models.Diet_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Calendar;
import java.util.List;

public class DietDao {

    public static Diet getDietByCalendar(Calendar calendar){
        return SQLite.select().from(Diet.class).where(Diet_Table.calendar.is(calendar)).querySingle();
    }

    public static void removeDietByCalendar(Calendar calendar){
        SQLite.delete().from(Diet.class).where(Diet_Table.calendar.is(calendar)).execute();
    }

    public static List<Diet> getAllDiet(){
        return SQLite.select().from(Diet.class).queryList();
    }
}
