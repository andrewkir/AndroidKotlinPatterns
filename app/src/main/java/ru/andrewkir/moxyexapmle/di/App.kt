package ru.andrewkir.moxyexapmle.di

import android.app.Application
import ru.andrewkir.moxyexapmle.utils.SharedPrefManager

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var prefs: SharedPrefManager
    }

    override fun onCreate() {
        prefs = SharedPrefManager.getInstance(applicationContext)
        super.onCreate()

        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .restModule(RestModule())
//            .mvpModule(MvpModule())
//            .chartModule(ChartModule())
            .build()
    }
}