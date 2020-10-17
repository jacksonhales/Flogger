package com.example.flogger.entity

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Relation

data class RoutineWithSets (
    @Embedded val routine: LiveData<Routine>,
    @Relation(
        parentColumn = "Id",
        entityColumn = "routineId"
    )
    val sets: LiveData<List<Set>>
)