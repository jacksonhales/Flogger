package com.example.flogger.repository

import androidx.lifecycle.LiveData
import com.example.flogger.dao.RoutineDao
import com.example.flogger.dao.SetDao
import com.example.flogger.entity.Set
import com.example.flogger.entity.Routine
import javax.inject.Inject

class RoutineRepository @Inject constructor(private val routineDao: RoutineDao) {

    fun getRoutines() = routineDao.getRoutines()

    fun getRoutineById(routineId: Long): Routine {
        return routineDao.getRoutineById(routineId)
    }

    suspend fun insert(routine: Routine) {
        routineDao.insert(routine)
    }

    suspend fun update(routine: Routine) {
        routineDao.update(routine)
    }

    suspend fun delete(routine: Routine) {
        routineDao.delete(routine)
    }
}