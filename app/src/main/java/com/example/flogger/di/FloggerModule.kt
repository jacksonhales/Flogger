package com.example.flogger.di

import android.content.Context
import com.example.flogger.dao.RoutineDao
import com.example.flogger.dao.SetDao
import com.example.flogger.database.FloggerRoomDatabase
import com.example.flogger.repository.RoutineRepository
import com.example.flogger.repository.SetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object FloggerModule {

    @Provides
    fun provideRoutineDao(@ApplicationContext appContext: Context) : RoutineDao {
        return FloggerRoomDatabase.getDatabase(appContext).routineDao()
    }

    @Provides
    fun provideSetDao(@ApplicationContext appContext: Context) : SetDao {
        return FloggerRoomDatabase.getDatabase(appContext).setDao()
    }

    @Provides
    fun provideRoutineRepository(routineDao: RoutineDao) = RoutineRepository(routineDao)

    @Provides
    fun provideSetRepository(setDao: SetDao) = SetRepository(setDao)

}