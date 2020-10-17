package com.example.flogger

import androidx.lifecycle.LiveData

// Declared the DAO as private prop in the constructor. Pass in DAO instead of whole db, only need access to DAO
class RoutineRepository (private val routineDao: RoutineDao) {

    // Room executes all queries on a separate thread
    // Observed LiveData will notify the observer when changed
    val allRoutines: LiveData<List<Routine>> = routineDao.getRoutines()

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