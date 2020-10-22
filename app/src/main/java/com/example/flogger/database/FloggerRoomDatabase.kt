package com.example.flogger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flogger.dao.RoutineDao
import com.example.flogger.dao.SetDao
import com.example.flogger.entity.Routine
import com.example.flogger.entity.Set
import com.example.flogger.enumeration.ExerciseType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Room database annotation
@Database (entities = [Routine::class, Set::class], version = 6, exportSchema = false)
@TypeConverters(Converter::class)
public abstract class FloggerRoomDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutineDao
    abstract fun setDao(): SetDao

    private class RoutineDatabaseCallback() : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
                database -> GlobalScope.launch { populateDatabase(database.routineDao(), database.setDao()) }
            }
        }

        suspend fun populateDatabase(routineDao: RoutineDao, setDao: SetDao) {
            // delete all db content
            setDao.deleteAll()
            routineDao.deleteAll()

            // add initial data
            val routinesToAdd = listOf(
                Routine(0, "Strength"),
                Routine(0, "Cardio"),
                Routine(0, "Flexibility")
            )

            val setsToAdd = listOf(
                Set(0, 1, "Pushups", ExerciseType.TIME_BASED, 20, 0),
                Set(0, 2, "Pullups", ExerciseType.REP_BASED, 10, 0)
            )

            for (r in routinesToAdd)
            {
                val routineId = routineDao.insert(r)
                for (s in setsToAdd) {
                    s.ownerId = routineId
                    setDao.insert(s)
                }
            }
        }
    }

    companion object {
        // singleton (prevents multiple instances of database opening at same time)
        @Volatile
        private var INSTANCE: FloggerRoomDatabase? = null

        fun getDatabase(context: Context): FloggerRoomDatabase {
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
                ).fallbackToDestructiveMigration().addCallback(RoutineDatabaseCallback()).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}