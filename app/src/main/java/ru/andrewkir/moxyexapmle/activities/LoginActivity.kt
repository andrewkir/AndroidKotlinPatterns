package ru.andrewkir.moxyexapmle.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.spin_kit
import ru.andrewkir.moxyexapmle.R
import ru.andrewkir.moxyexapmle.models.User
import ru.andrewkir.moxyexapmle.presenters.LoginPresenter
import ru.andrewkir.moxyexapmle.views.AuthView

class LoginActivity :  MvpAppCompatActivity(), AuthView {
    override fun updateResultData(user: User) {
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun startReceiving() {
        spin_kit.visibility = View.VISIBLE

        editTextEmailLogin.isEnabled = false
        editTextPasswordLogin.isEnabled = false
        buttonLogin.isEnabled = false
    }

    override fun endReceiving(res: String) {
        spin_kit.visibility = View.INVISIBLE

        editTextEmailLogin.isEnabled = true
        editTextPasswordLogin.isEnabled = true
        buttonLogin.isEnabled = true
    }


    @InjectPresenter//(presenterId = "", tag="", type = PresenterType.LOCAL)
    lateinit var addRegisterPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        buttonLogin.setOnClickListener {
            val email = editTextEmailLogin.text.toString().trim()
            val password = editTextPasswordLogin.text.toString().trim()

            if (email.isEmpty()) {
                editTextEmailLogin.error = "Email required"
                editTextEmailLogin.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                editTextPasswordLogin.error = "Password required"
                editTextPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            addRegisterPresenter.loginUser(email, password)
        }
    }
}
