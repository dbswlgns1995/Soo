package com.jihoonyoon.soo.notepad.database;

import com.jihoonyoon.soo.notepad.models.Schedule;
import com.jihoonyoon.soo.notepad.models.Schedule_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Calendar;
import java.util.List;

public class ScheduleDao {

    public static Schedule getScheduleByCalendar(Calendar calendar){
        return SQLite.select().from(Schedule.class).where(Schedule_Table.calendar.is(calendar)).querySingle();
    }

    public static void removeScheduleByCalendar(Calendar calendar){
        SQLite.delete().from(Schedule.class).where(Schedule_Table.calendar.is(calendar)).execute();
    }

    public static List<Schedule> getAllSchedule(){
        return SQLite.select().from(Schedule.class).queryList();
    }
}
