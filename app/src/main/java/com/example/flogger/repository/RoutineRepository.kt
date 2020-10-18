package com.example.flogger.repository

import androidx.lifecycle.LiveData
import com.example.flogger.dao.RoutineDao
import com.example.flogger.entity.Routine
/*import com.example.flogger.entity.Set
import com.example.flogger.relationships.RoutineWithSets*/

// Declared the DAO as private prop in the constructor. Pass in DAO instead of whole db, only need access to DAO
class RoutineRepository (private val routineDao: RoutineDao) {

    // Room executes all queries on a separate thread
    // Observed LiveData will notify the observer when changed
    val allRoutines: LiveData<List<Routine>> = routineDao.getRoutines()

    suspend fun insert(routine: Routine) {
        routineDao.insert(routine)
    }
/*
    suspend fun insertWithSets(routineWithSets: RoutineWithSets) {
        val id = routineDao.insert(routineWithSets.routine)
        for (s : Set in routineWithSets.sets)
        {
            s.routineId = id
        }

        routineDao.insertSets(routineWithSets.sets)
    }*/

    suspend fun update(routine: Routine) {
        routineDao.update(routine)
    }

    suspend fun delete(routine: Routine) {
        routineDao.delete(routine)
    }


}