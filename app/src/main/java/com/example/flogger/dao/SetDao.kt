package com.example.flogger.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flogger.entity.Routine
import com.example.flogger.entity.Set

@Dao
interface SetDao {
    @Query("SELECT * from set_table WHERE ownerId=(:ownerId) ORDER BY performOrder ASC")
    fun getSetsByRoutineId(ownerId: Long): LiveData<List<Set>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(set: Set) : Long

    @Query("DELETE FROM set_table")
    suspend fun deleteAll()
}
