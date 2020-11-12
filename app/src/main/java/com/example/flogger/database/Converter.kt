package com.example.flogger.database

import androidx.room.TypeConverter
import com.example.flogger.enumeration.ExerciseType

object Converter {
    @TypeConverter
    @JvmStatic
    fun exerciseTypeToString(exerciseType: ExerciseType?) = exerciseType?.name

    @TypeConverter
    @JvmStatic
    fun stringToExerciseType(s: String?) = s?.let(ExerciseType::valueOf)
}

