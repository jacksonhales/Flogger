package com.example.flogger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// ViewModels dont survive apps process being killed, use Saved State module for ViewModels for a workaround
class RoutineViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: RoutineRepository

    // use LiveData to cache what getRoutines() returns has these benefits:
    // - We can put an observer on the data (instead of polling for changes) and only when the UI when the data actually changes
    // - Repository is completely separated from the UI through the viewmodel
    val allRoutines: LiveData<List<Routine>>

    init {
        val routineDao = FloggerRoomDatabase.getDatabase(application, viewModelScope).routineDao()
        repository = RoutineRepository(routineDao)
        allRoutines = repository.allRoutines
    }

    // new coroutine launch for inserting in a non-blocking way
    fun insert(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(routine)
    }

    fun update(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(routine)
    }

    fun delete(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(routine)
    }

}