package com.example.flogger.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "workout_table")
data class Workout(@PrimaryKey(autoGenerate = true) val id: Int = 0,
               @ColumnInfo(name = "date") val date: Date,
               @ColumnInfo(name = "routineId") val routineId: Int
) : Parcelable