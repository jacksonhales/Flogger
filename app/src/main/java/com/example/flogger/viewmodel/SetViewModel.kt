package com.example.flogger.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.flogger.entity.Set
import com.example.flogger.repository.SetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetViewModel @ViewModelInject constructor(private val setRepository: SetRepository)
    : ViewModel(), LifecycleObserver {

    var sets: LiveData<MutableList<Set>> = MutableLiveData()

    fun getSets(routineId: Long){
        viewModelScope.launch {
            sets = setRepository.getSetsByRoutineId(routineId)
        }
    }

    fun insertSet(set: Set) = viewModelScope.launch(Dispatchers.IO) {
        setRepository.insert(set)
    }

    fun updateSet(set: Set) = viewModelScope.launch(Dispatchers.IO) {
        setRepository.update(set)
    }

    fun deleteSet(set: Set) = viewModelScope.launch(Dispatchers.IO) {
        setRepository.delete(set)
    }
}