package ru.andrewkir.moxyexapmle.api

import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import ru.andrewkir.moxyexapmle.models.DefaultResponse
import ru.andrewkir.moxyexapmle.models.User
import ru.andrewkir.moxyexapmle.utils.LOGIN_URL
import ru.andrewkir.moxyexapmle.utils.REFRESH_TOKEN
import ru.andrewkir.moxyexapmle.utils.REGISTER_URL
import ru.andrewkir.moxyexapmle.di.App.Companion.prefs
import ru.andrewkir.moxyexapmle.models.TokenResponse
import java.util.*

interface MainApi {
    //    @FormUrlEncoded
    @POST(REGISTER_URL)
    fun createUser(
//        @Field("email") email:String,
//        @Field("name") name:String,
//        @Field("password") password:String,
//        @Field("school") school:String,
        @Body user: User
    ): Observable<DefaultResponse>

    @POST(LOGIN_URL)
    fun loginUser(
        @Body user: JSONObject
    ): Observable<DefaultResponse>


    @POST(REFRESH_TOKEN)
    fun requestAccessToken(

    ): Call<TokenResponse>

    @GET("getuser")
    fun getUser(): Observable<DefaultResponse>
}