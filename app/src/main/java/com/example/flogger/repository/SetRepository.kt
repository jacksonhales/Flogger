package com.example.flogger.repository

import androidx.lifecycle.LiveData
import com.example.flogger.dao.SetDao
import com.example.flogger.entity.Set
import javax.inject.Inject

class SetRepository @Inject constructor(private val setDao: SetDao) {

    fun getSetsByRoutineId(routineId: Long): LiveData<MutableList<Set>> {
        return setDao.getSetsByRoutineId(routineId)
    }

    suspend fun insert(set: Set) {
        setDao.insert(set)
    }

    suspend fun update(set: Set) {
        setDao.update(set)
    }

    suspend fun delete(set: Set) {
        setDao.delete(set)
    }
}