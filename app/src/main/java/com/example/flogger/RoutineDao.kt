package com.example.flogger

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoutineDao {
    @Query ("SELECT * from routine_table ORDER BY id ASC")
    fun getRoutines(): LiveData<List<Routine>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine: Routine)

    @Update
    fun update(routine:Routine)

    @Query("DELETE FROM routine_table")
    suspend fun deleteAll()

    @Delete
    fun delete(routine: Routine)

}