//package com.jihoonyoon.soo.calendar.dao
//
//import androidx.room.*
//import com.jihoonyoon.soo.calendar.model.Diet
//import com.jihoonyoon.soo.calendar.model.Schedule
//import java.util.*
//
//@Dao
//interface ScheduleDao {
//    @Insert
//    fun insertSchedule(schedule: Schedule)
//
//    @Delete
//    fun deleteSchedule(schedule: Schedule)
//
//    @Update
//    fun updateSchedule(schedule: Schedule)
//
//    @Query("Select * From Schedule")
//    fun selectSchedule(): List<Schedule>
//
//    @Query("Select * From Schedule Where calendar = :calendar")
//    fun selectScheduleByCalendar(calendar: Calendar): Schedule
//}