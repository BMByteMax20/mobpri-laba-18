package com.example.myapplication.di

import com.example.myapplication.utils.AndroidLogger
import com.example.myapplication.utils.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {

    @Binds
    @Singleton
    abstract fun bindLogger(logger: AndroidLogger): Logger
}