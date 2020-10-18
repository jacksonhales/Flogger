package com.example.flogger.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "routine_table")
data class Routine(
                    @PrimaryKey(autoGenerate = true) val id: Long = 0,
                    @ColumnInfo(name = "name") val name: String
) : Parcelable