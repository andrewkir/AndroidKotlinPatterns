package ru.andrewkir.moxyexapmle.data

import android.content.Context
import ru.andrewkir.moxyexapmle.models.User

class SharedPrefManager  private constructor(private val context: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("email", "") != ""
        }

    val user: User
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
//                sharedPreferences.getInt("id", -1)
                email = sharedPreferences.getString("email", ""),
                password = sharedPreferences.getString("name", ""),
                name=sharedPreferences.getString("name", ""),
                school=sharedPreferences.getString("school", ""),
                access_token=sharedPreferences.getString("access_token", "dev_access_token"),
                refresh_token = sharedPreferences.getString("refresh_token", "dev_refresh")
            )
        }


    fun saveUser(user: User) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("id", user.email)

        editor.putString("email", user.email)
        editor.putString("name", user.name)
        editor.putString("school", user.school)
        editor.putString("access_token", user.access_token)
        editor.putString("refresh_token", user.refresh_token)

        editor.apply()

    }

    fun clear() {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance =
                    SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }
}