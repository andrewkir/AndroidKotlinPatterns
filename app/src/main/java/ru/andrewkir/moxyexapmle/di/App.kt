package ru.andrewkir.moxyexapmle.di

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.andrewkir.moxyexapmle.data.SharedPrefManager

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var prefs: SharedPrefManager
    }

    override fun onCreate() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("mylist.realm").build()
        Realm.setDefaultConfiguration(config)

        super.onCreate()
        prefs = SharedPrefManager.getInstance(applicationContext)
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