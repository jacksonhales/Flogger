package com.example.flogger

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoutineDao {
    @Query ("SELECT * from routine_table ORDER BY id ASC")
    fun getRoutines(): LiveData<List<Routine>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine: Routine)

    @Query("DELETE FROM routine_table")
    suspend fun deleteAll()

}