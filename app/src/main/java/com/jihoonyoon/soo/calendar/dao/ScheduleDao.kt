package com.jihoonyoon.soo.calendar.dao

import com.jihoonyoon.soo.calendar.model.Schedule
import com.raizlabs.android.dbflow.sql.language.SQLite
import java.util.*


object ScheduleDao {
//    fun insertSchedule(schedule: Schedule)
//
//    fun deleteSchedule(schedule: Schedule)
//
//    fun updateSchedule(schedule: Schedule)

    fun selectSchedule(): List<Schedule> {
        return SQLite.select().from(Schedule::class.java).queryList()
    }

    //fun selectScheduleByCalendar(calendar: Calendar): Schedule
}

//object NotesDAO {
//    fun getLatestNotes(folder: Folder?): List<Note> {
//        return if (folder == null) SQLite.select().from(Note::class.java)
//            .orderBy(Note_Table.createdAt, false).queryList() else FolderNoteDAO.getLatestNotes(
//            folder
//        )
//    }
//
//    fun getNote(noteId: Int): Note? {
//        return SQLite.select().from(Note::class.java).where(Note_Table.id.`is`(noteId))
//            .querySingle()
//    }
//}