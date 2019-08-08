package ru.andrewkir.moxyexapmle.di

import dagger.Component
import ru.andrewkir.moxyexapmle.activities.MainActivity
import ru.andrewkir.moxyexapmle.activities.RegisterActivity
import ru.andrewkir.moxyexapmle.presenters.LoginPresenter
import ru.andrewkir.moxyexapmle.presenters.RegisterPresenter
import javax.inject.Singleton

@Component(modules = arrayOf(RestModule::class, AppModule::class))
@Singleton
interface AppComponent {
    fun inject(registerActivity: RegisterActivity)
    fun inject(presenter: RegisterPresenter)
    fun inject(presenter: LoginPresenter)
    fun inject(mainActivity: MainActivity)
}