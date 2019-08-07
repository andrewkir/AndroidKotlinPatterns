package ru.andrewkir.moxyexapmle.presenters

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import ru.andrewkir.moxyexapmle.api.MainApi
import ru.andrewkir.moxyexapmle.di.App
import ru.andrewkir.moxyexapmle.views.AuthView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class LoginPresenter: MvpPresenter<AuthView>() {
    //внедряем источник данных
    @Inject
    lateinit var mainApi: MainApi

    //инициализируем компоненты Даггера
    init {
        App.appComponent.inject(this)
    }


    @SuppressLint("CheckResult")
    fun loginUser(email:String, password:String){
        val body = JSONObject()
        body.put("email",email)
        body.put("password", password)

        viewState.startReceiving()
        mainApi.loginUser(body)
            //DEBUG
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.endReceiving(it.message.toString())
                App.prefs.saveUser(it.message)
            }, {
                viewState.endReceiving(it.message.toString())
                it.printStackTrace()
                if(it::class.java == java.net.UnknownHostException::class.java){
                    viewState.showError("Please check the connection")
                } else {
                    viewState.showError("The fuck is this")
                }
            })
    }
}