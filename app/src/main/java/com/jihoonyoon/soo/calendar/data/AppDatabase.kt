package com.jihoonyoon.soo.calendar.data

//
//@Database(
//    entities = [Schedule::class, Diet::class],
//    version = 1,
//    exportSchema = false
//)
//
//abstract class AppDatabase: RoomDatabase() {
//
//    abstract fun dietDao(): DietDao
//    abstract fun scheduleDao(): ScheduleDao
//
//    companion object {
//        const val DBNAME = "room_db"
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase? {
//            if (INSTANCE == null) {
//                synchronized(AppDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(
//                        context,
//                        AppDatabase::class.java,
//                        DBNAME
//                    )
//                        .build()
//                }
//            }
//            return INSTANCE
//        }
//    }
//}