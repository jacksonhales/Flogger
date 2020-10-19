package com.example.flogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "set_table",
    foreignKeys = [ForeignKey(
        entity = Routine::class,
        parentColumns = ["routineId"],
        childColumns = ["ownerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Set(
               @PrimaryKey(autoGenerate = true) val setId: Long = 0,
               @ColumnInfo(name = "performOrder") val performOrder: Int,
               @ColumnInfo(name = "ownerId") var ownerId: Long
)
