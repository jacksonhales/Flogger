package com.example.flogger

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Room database annotation
@Database (entities = [Routine::class], version = 1, exportSchema = false)
public abstract class FloggerRoomDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutineDao

    private class RoutineDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
                database -> scope.launch { populateDatabase(database.routineDao()) }
            }
        }

        suspend fun populateDatabase(routineDao: RoutineDao) {
            // delete all db content
            routineDao.deleteAll()

            // add initial data
            var routine = Routine(0, "Strength" )
            routineDao.insert(routine)
            routine = Routine(0, "Flexibility")
            routineDao.insert(routine)
            routine = Routine(0, "Cardio")
            routineDao.insert(routine)
        }
    }

    companion object {
        // singleton (prevents multiple instances of database opening at same time)
        @Volatile
        private var INSTANCE: FloggerRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FloggerRoomDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            // if db instance empty then make one
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FloggerRoomDatabase::class.java,
                    "flogger_database"
                )/*.addCallback(RoutineDatabaseCallback(scope))*/.build()
                INSTANCE = instance
                return instance
            }
        }
    }
}