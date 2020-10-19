package com.example.flogger.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.flogger.database.FloggerRoomDatabase
import com.example.flogger.entity.Routine
import com.example.flogger.repository.RoutineRepository
import kotlinx.coroutines.launch

class RoutinesViewModel @ViewModelInject constructor(private val routineRepository: RoutineRepository) :
    ViewModel(), LifecycleObserver {

    var routines: LiveData<MutableList<Routine>> = MutableLiveData<MutableList<Routine>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchRoutines(){
        viewModelScope.launch {
            routines = routineRepository.getRoutines()
        }
    }
}