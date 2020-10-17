package com.example.flogger.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "set_table")
data class Set(
               @PrimaryKey(autoGenerate = true) val id: Int = 0,
               @ColumnInfo(name = "order") val order: Int,
               @ColumnInfo(name = "routineId") val routineId: Int
) : Parcelable