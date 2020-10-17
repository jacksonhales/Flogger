package com.example.flogger.entity

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutAndRoutine(
    @Embedded val workout: LiveData<Workout>,
    @Relation(
        parentColumn = "Id",
        entityColumn = "routineId"
    )
    val routine: LiveData<Routine>
)