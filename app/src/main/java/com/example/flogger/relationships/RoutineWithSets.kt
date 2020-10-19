package com.example.flogger.relationships

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Relation
import com.example.flogger.entity.Routine
import com.example.flogger.entity.Set

data class RoutineWithSets (
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "routineId",
        entityColumn = "ownerId",
        entity = Set::class
    )
    val sets: List<Set>
)
