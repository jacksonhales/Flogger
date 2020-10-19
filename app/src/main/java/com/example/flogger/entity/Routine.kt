package com.example.flogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_table")
data class Routine(
                    @PrimaryKey(autoGenerate = true) val routineId: Long = 0,
                    @ColumnInfo(name = "name") val name: String
)