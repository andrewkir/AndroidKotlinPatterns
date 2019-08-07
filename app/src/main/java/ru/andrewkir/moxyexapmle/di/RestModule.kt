package ru.andrewkir.moxyexapmle.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.Nullable
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.andrewkir.moxyexapmle.api.MainApi
import ru.andrewkir.moxyexapmle.di.App.Companion.prefs
import ru.andrewkir.moxyexapmle.utils.BASE_URL
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import java.io.IOException
import okhttp3.OkHttpClient
import android.util.Log


@Module
class RestModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()


    class HeaderAccessTokenInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer "+ prefs.user.access_token)
                    .build()
            )
        }
    }
    class HeaderRefreshTokenInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer "+ prefs.user.refresh_token)
                    .build()
            )
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val client =  OkHttpClient.Builder()
            .addInterceptor(HeaderAccessTokenInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
        client.authenticator(object : Authenticator {
            @Nullable
            @Throws(IOException::class)
            override fun authenticate(route: Route?, response: Response): Request? {
                // check if "the failed request Authorization key" is different from new authorization key
                // to prevent looping the request for new key
                if (!response.request.header("Authorization").equals(prefs.user.access_token)) {
                    return null // stop the authenticator from trying to renew the authorization key
                }
                // request new key
                var accessToken: String? = null
                val apiService = getRetrofitRefresh().create(MainApi::class.java)
                val call = apiService.requestAccessToken()
                try {
                    val responseCall = call.execute()
                    accessToken = responseCall.body()?.access_token
                    val user = prefs.user
                    user.access_token = accessToken!!.toString()
                    prefs.saveUser(user)
                } catch (ex: Exception) {
                    Log.d("ERROR", "onResponse: $ex")
                }
                return if (accessToken != null) {
                    // retry the failed 401 request with new access token
                    response.request.newBuilder()
                        .header("Authorization", accessToken) // use the new access token
                        .build()
                } else
                    null
            }
        })
        return client.build()
    }

    fun getRetrofitRefresh(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(HeaderRefreshTokenInterceptor())

            val retrofitRefresh = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        return retrofitRefresh
    }

    @Provides
    @Singleton
    @Named("MAIN_API")
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideMainApiService(@Named("MAIN_API") retrofit: Retrofit): MainApi =
        retrofit.create(MainApi::class.java)
}