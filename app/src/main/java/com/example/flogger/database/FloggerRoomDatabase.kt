package com.example.flogger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flogger.dao.RoutineDao
import com.example.flogger.dao.SetDao
import com.example.flogger.entity.Routine
import com.example.flogger.entity.Set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Room database annotation
@Database (entities = [Routine::class, Set::class], version = 4, exportSchema = false)
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
            val routine = Routine(0, "Strength" )
            val routineId = routineDao.insert(routine)

            var set = Set(0, 1, routineId)
            setDao.insert(set)

            set = Set(0, 2, routineId)
            setDao.insert(set)
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
                ).fallbackToDestructiveMigration()/*.addCallback(RoutineDatabaseCallback())*/.build()
                INSTANCE = instance
                return instance
            }
        }
    }
}