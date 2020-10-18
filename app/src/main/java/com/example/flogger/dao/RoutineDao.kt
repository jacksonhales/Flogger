package com.example.flogger.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flogger.entity.Routine
/*import com.example.flogger.entity.Set*/
/*import com.example.flogger.relationships.RoutineWithSets*/

@Dao
interface RoutineDao {
    @Query ("SELECT * from routine_table ORDER BY id ASC")
    fun getRoutines(): LiveData<List<Routine>>

/*    @Transaction
    @Query("SELECT * FROM routine_table")
    fun getRoutineWithSets(): LiveData<List<RoutineWithSets>>*/

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