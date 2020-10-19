package com.example.flogger.database

import android.content.Context
import android.provider.Settings
import com.example.flogger.dao.RoutineDao
import com.example.flogger.repository.RoutineRepository
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
    fun provideRoutineRepository(routineDao: RoutineDao) = RoutineRepository(routineDao)

}