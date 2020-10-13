package com.example.flogger

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "routine_table")
data class Routine(@PrimaryKey(autoGenerate = true) val id: Int,
                    @ColumnInfo(name = "name") val name: String)