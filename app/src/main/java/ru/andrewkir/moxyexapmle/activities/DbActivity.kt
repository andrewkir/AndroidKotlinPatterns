package ru.andrewkir.moxyexapmle.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_db.*
import ru.andrewkir.moxyexapmle.R
import ru.andrewkir.moxyexapmle.models.MyListObject

class DbActivity : AppCompatActivity() {
    lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)

        realm = Realm.getDefaultInstance()
        dbSave.setOnClickListener {

        }
    }
}
