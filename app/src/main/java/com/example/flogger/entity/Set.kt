package com.example.flogger.entity

import android.os.Parcelable
import androidx.room.*
import com.example.flogger.enumeration.ExerciseType
import kotlinx.parcelize.Parcelize

@Entity(tableName = "set_table",
    foreignKeys = [ForeignKey(
        entity = Routine::class,
        parentColumns = ["routineId"],
        childColumns = ["ownerId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["ownerId"], name ="ownerId")]
)
@Parcelize
data class Set(
    @PrimaryKey(autoGenerate = true) val setId: Long = 0,
    @ColumnInfo(name = "performOrder") val performOrder: Int,
    @ColumnInfo(name = "exercise") val exercise : String,
    @ColumnInfo(name = "exerciseType") val exerciseType: ExerciseType,
    @ColumnInfo(name = "goal") val goal: Int,
    @ColumnInfo(name = "ownerId") var ownerId: Long
) : Parcelable
