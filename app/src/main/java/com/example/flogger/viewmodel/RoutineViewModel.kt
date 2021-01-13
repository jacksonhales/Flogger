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
    var singleRoutine: MutableLiveData<Routine> = MutableLiveData()
    var largestDisplayOrder: MutableLiveData<Int> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getRoutines(){
        viewModelScope.launch {
            routines = routineRepository.getRoutines()
        }
    }

    fun getRoutine(routineId: Long) = viewModelScope.launch(Dispatchers.IO){
        val routine = routineRepository.getRoutineById(routineId)
        singleRoutine.postValue(routine)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getLargestRoutineDisplayOrder() = viewModelScope.launch(Dispatchers.IO) {
        val displayOrder = routineRepository.getLargestRoutineDisplayOrder()
        largestDisplayOrder.postValue(displayOrder)
    }

    fun insertRoutine(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        routineRepository.insert(routine)
    }

    fun updateRoutine(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        routineRepository.update(routine)
    }

    fun deleteRoutine(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        routineRepository.delete(routine)
    }
}