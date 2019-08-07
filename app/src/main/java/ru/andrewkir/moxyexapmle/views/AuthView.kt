package ru.andrewkir.moxyexapmle.views

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.andrewkir.moxyexapmle.models.User


@StateStrategyType(OneExecutionStateStrategy::class)
interface AuthView: MvpView {
    fun showMessage(message: String)
    fun showError(message: String)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startReceiving()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun endReceiving(res:String)
    fun updateResultData(user:User)
}
