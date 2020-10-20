package com.example.flogger.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.flogger.entity.Set
import com.example.flogger.repository.SetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditRoutineViewModel @ViewModelInject constructor(private val setRepository: SetRepository)
    : ViewModel(), LifecycleObserver {

    var sets: LiveData<MutableList<Set>> = MutableLiveData()

    fun getSets(id: Long){
        viewModelScope.launch {
            sets = setRepository.getSetsByRoutineId(id)
        }
    }

    fun deleteSet(set: Set) = viewModelScope.launch(Dispatchers.IO) {
        setRepository.delete(set)
    }

}