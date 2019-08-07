package ru.andrewkir.moxyexapmle.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import ru.andrewkir.moxyexapmle.R
import ru.andrewkir.moxyexapmle.models.User
import ru.andrewkir.moxyexapmle.presenters.RegisterPresenter
import ru.andrewkir.moxyexapmle.views.AuthView

class RegisterActivity : MvpAppCompatActivity(), AuthView {
    override fun updateResultData(user: User) {
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }


    override fun startReceiving() {
        spin_kit.visibility = View.VISIBLE

        editTextEmail.isEnabled = false
        editTextPassword.isEnabled = false
        editTextName.isEnabled = false
        editTextSchool.isEnabled = false
        buttonSignUp.isEnabled = false
    }

    override fun endReceiving(res: String) {
        spin_kit.visibility = View.INVISIBLE
        editTextEmail.isEnabled = true
        editTextPassword.isEnabled = true
        editTextName.isEnabled = true
        editTextSchool.isEnabled = true
        buttonSignUp.isEnabled = true

    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @InjectPresenter//(presenterId = "", tag="", type = PresenterType.LOCAL)
    lateinit var addRegisterPresenter: RegisterPresenter

    @ProvidePresenter
    fun provideMainPresenter(): RegisterPresenter {
        return RegisterPresenter() //здесь могут быть параметры для конструктора
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//      Ограничить число нажатий (убрать из потока, который чаще, чем число)
//        buttonSignUp.clicks()
//            .throttleFirst(1000, TimeUnit.MILLISECONDS)
//            .subscribe{
//                //doSTUFF
//                Toast.makeText(this, "hey", Toast.LENGTH_SHORT).show()
//            }
        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val name = editTextName.text.toString().trim()
            val school = editTextSchool.text.toString().trim()

            if(email.isEmpty()){
                editTextEmail.error = "Email required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }
            if(name.isEmpty()){
                editTextName.error = "Name required"
                editTextName.requestFocus()
                return@setOnClickListener
            }
            if(school.isEmpty()){
                editTextSchool.error = "School required"
                editTextSchool.requestFocus()
                return@setOnClickListener
            }

            addRegisterPresenter.createUser(email, password, name, school)
        }
    }
}
