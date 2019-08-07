package ru.andrewkir.moxyexapmle.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.andrewkir.moxyexapmle.R
import ru.andrewkir.moxyexapmle.api.MainApi
import ru.andrewkir.moxyexapmle.di.App
import ru.andrewkir.moxyexapmle.di.App.Companion.prefs
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    //внедряем источник данных
    @Inject
    lateinit var mainApi: MainApi

    //инициализируем компоненты Даггера
    init {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateData()

        exRegisterButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent,1)
        }

        exLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent,1)
        }

        statusUpdate.setOnClickListener {
            updateData()
        }
        statusLogout.setOnClickListener {
            prefs.clear()
            updateData()
        }

        exStatusToken.setOnClickListener {
            tokenText.text = prefs.user.access_token
        }

        checkToken.setOnClickListener {
            mainApi.getUser()//DEBUG
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this,it.message.email, Toast.LENGTH_SHORT).show()
                }, {
                    it.printStackTrace()
                    Toast.makeText(this, "The fuck is this", Toast.LENGTH_SHORT).show()
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> updateData()
        }
    }

    fun updateData(){
        mainStatus.text = prefs.isLoggedIn.toString()
        if (prefs.isLoggedIn) {
            statusName.text = prefs.user.name
            tokenText.text = prefs.user.access_token
        } else {
            statusName.text = "username"
        }
    }
}
