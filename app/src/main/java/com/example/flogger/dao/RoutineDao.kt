package com.example.flogger.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flogger.entity.Routine
/*import com.example.flogger.entity.Set*/
import com.example.flogger.relationships.RoutineWithSets

@Dao
interface RoutineDao {
    @Query ("SELECT * FROM routine_table ORDER BY routineId ASC")
    fun getAllRoutines(): LiveData<List<Routine>>

    @Transaction
    @Query("SELECT * FROM routine_table")
    fun getAllRoutinesWithSets(): LiveData<List<RoutineWithSets>>

    @Transaction
    @Query("SELECT * FROM routine_table WHERE routineId=(:routineId) ORDER BY routineId ASC")
    fun getRoutineWithSetsById(routineId: Long): RoutineWithSets

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine: Routine) : Long

/*    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSets(sets : List<Set>)*/

    @Update
    suspend fun update(routine: Routine)

    @Query("DELETE FROM routine_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(routine: Routine)



}