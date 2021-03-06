package com.example.flogger.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flogger.entity.Routine

@Dao
interface RoutineDao {
    @Query ("SELECT * FROM routine_table ORDER BY displayOrder ASC")
    fun getRoutines(): LiveData<MutableList<Routine>>

    @Query ("SELECT * FROM routine_table WHERE routineId=(:routineId)")
    suspend fun getRoutineById(routineId: Long): Routine

    @Query ("SELECT MAX(displayOrder) FROM routine_table")
    suspend fun getLargestRoutineDisplayOrder(): Int

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine: Routine) : Long

    @Update
    suspend fun update(routine: Routine)

    @Query("DELETE FROM routine_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(routine: Routine)

}