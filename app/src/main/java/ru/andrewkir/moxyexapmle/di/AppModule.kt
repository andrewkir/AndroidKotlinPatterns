package ru.andrewkir.moxyexapmle.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//класс для предоставления контекста в любой части приложения
@Module
class AppModule(private val app: App) {
    @Provides
    @Singleton
    fun provideContext(): Context = app
}