package com.example.flogger.dao

import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Transaction
import com.example.flogger.entity.RoutineWithSets

interface RoutineWithSetsDao {
    @Transaction
    @Query("SELECT * FROM routine_table")
    fun getRoutineWithSets(): LiveData<List<RoutineWithSets>>
}