package com.example.uid_finalproject.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


data class User(val id: Int, val name: String, val email: String)
data class NewUser(val name: String, val email: String)


data class SensorData(val type: String, val value: String, val unit: String)
data class SensorUpdateResponse(val id: Int, val type: String, val value: String, val unit: String)

interface ApiService {
    // --- User Endpoints ---
    @GET("users/1")
    suspend fun getUser(): User

    @POST("users")
    suspend fun addUser(@Body newUser: NewUser): Response<User>

    // --- Sensor Endpoint ---
    @POST("posts")
    suspend fun postSensorData(@Body sensorData: SensorData): Response<SensorUpdateResponse>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): ApiService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}