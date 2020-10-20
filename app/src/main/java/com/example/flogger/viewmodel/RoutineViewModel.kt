package com.example.flogger.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.flogger.entity.Routine
import com.example.flogger.repository.RoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutineViewModel @ViewModelInject constructor(private val routineRepository: RoutineRepository) :
    ViewModel(), LifecycleObserver {

    var routines: LiveData<MutableList<Routine>> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getRoutines(){
        viewModelScope.launch {
            routines = routineRepository.getRoutines()
        }
    }

    fun getRoutine(routineId: Long) : Routine {
        return routineRepository.getRoutineById(routineId)
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