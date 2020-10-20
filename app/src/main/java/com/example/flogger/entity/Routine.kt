package com.example.flogger.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "routine_table")
@Parcelize
data class Routine constructor(
                    @PrimaryKey(autoGenerate = true) val routineId: Long = 0,
                    @ColumnInfo(name = "name") val name: String
) : Parcelable