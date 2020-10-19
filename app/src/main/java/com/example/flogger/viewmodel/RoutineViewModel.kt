package com.example.flogger.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.flogger.entity.Routine
import com.example.flogger.repository.RoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RoutineViewModel @ViewModelInject constructor(private val routineRepository: RoutineRepository) :
    ViewModel(), LifecycleObserver {

    var routine = Routine(0, "")

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getRoutine(){
        viewModelScope.launch {
            /*routine = routineRepository.getRoutineById(routineId)*/
        }
    }

    fun insert(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        routineRepository.insert(routine)
    }

    fun update(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        routineRepository.update(routine)
    }

    fun delete(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        routineRepository.delete(routine)
    }
}