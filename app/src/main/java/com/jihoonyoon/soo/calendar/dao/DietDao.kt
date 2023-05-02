//package com.jihoonyoon.soo.calendar.dao
//
//import androidx.room.*
//import com.jihoonyoon.soo.calendar.model.Diet
//import java.util.*
//
//@Dao
//interface DietDao {
//
//    @Insert
//    fun insertDiet(diet: Diet)
//
//    @Delete
//    fun deleteDiet(diet: Diet)
//
//    @Update
//    fun updateDiet(diet: Diet)
//
//    @Query("Select * From Diet")
//    fun selectDiet(): List<Diet>
//
//    @Query("Select * From Diet Where calendar = :calendar")
//    fun selectDietByCalendar(calendar: Calendar): Diet
//}