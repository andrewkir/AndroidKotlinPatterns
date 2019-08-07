package ru.andrewkir.moxyexapmle.presenters

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrewkir.moxyexapmle.api.MainApi
import ru.andrewkir.moxyexapmle.di.App
import ru.andrewkir.moxyexapmle.di.App.Companion.prefs
import ru.andrewkir.moxyexapmle.models.User
import ru.andrewkir.moxyexapmle.views.AuthView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class RegisterPresenter: MvpPresenter<AuthView>() {

    //внедряем источник данных
    @Inject
    lateinit var mainApi: MainApi

    //инициализируем компоненты Даггера
    init {
        App.appComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    fun createUser(email:String, name:String, password:String, school:String){
        viewState.startReceiving()
        mainApi.createUser(User(email,name,password,school))

            //DEBUG
            .delay(2, TimeUnit.SECONDS)


            //определяем отдельный поток для отправки данных
            .subscribeOn(Schedulers.io())
            //получаем данные в основном потоке
            .observeOn(AndroidSchedulers.mainThread())
            //преобразуем List<GeckoCoin> в Observable<GeckoCoin>
//            .flatMap { Observable.fromIterable(it) }
            //наполняем поля элемента списка для адаптера
            .doOnNext {
//                viewState.endReceiving(it.message.toString())
            }
            .doOnError {
                viewState.endReceiving(it.message.toString())
            }
            .doOnComplete {
                viewState.showMessage("complete")
            }
            //подписывает Observer на Observable
            .subscribe({
                viewState.showMessage(it.message.access_token)
                viewState.endReceiving(it.message.toString())
                viewState.updateResultData(it.message)
                prefs.saveUser(it.message)
            }, {
                viewState.showError(it.message.toString())
                viewState.endReceiving(it.message.toString())
                it.printStackTrace()
            })
    }


}