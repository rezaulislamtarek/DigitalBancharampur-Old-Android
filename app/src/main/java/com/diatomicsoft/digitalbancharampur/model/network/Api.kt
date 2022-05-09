package com.diatomicsoft.digitalbancharampur.model.network

import com.diatomicsoft.digitalbancharampur.model.data.*
import com.diatomicsoft.digitalbancharampur.util.BASEURL
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface Api {

    @GET("/api/others/all/{type}")
    suspend fun getPlacesByType(
        @Path("type") type: String
    ): Response<List<Places>>

    @GET("/api/cars/all")
    suspend fun getCars(): Response<List<Car>>

    @GET("/api/ambulance/all")
    suspend fun getAllAmbulance(): Response<List<Ambulance>>

    @GET("/api/auth/users/{id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): Response<User>

    @FormUrlEncoded
    @POST("/api/auth/login")
    suspend fun userLogin(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Response<Auth>

    @Multipart
    @POST("/api/auth/registration")
    suspend fun signUp(
        @Part("name") name: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("password") password: RequestBody,
        @Part("address") address: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<Auth>


    @Multipart
    @POST("/api/others/add")
    suspend fun addPlace(
        @Part("title") title: RequestBody,
        @Part("type") type: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("details") details: RequestBody,
        @Part("address") address: RequestBody,
        @Part("contributorId") contributorId: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<CommonResponse>

    @Multipart
    @POST("/api/cars/add")
    suspend fun addCar(
        @Part("driverName") driverName: RequestBody,
        @Part("ownerName") ownerName: RequestBody,
        @Part("ownerNumber") ownerNumber: RequestBody,
        @Part("driverNumber") driverNumber: RequestBody,
        @Part("address") address: RequestBody,
        @Part("contributorId") contributorId: RequestBody,
        @Part("carRegNo") carRegNo: RequestBody,
        @Part("others") others: RequestBody,
        @Part("carRoute") carRoute: RequestBody,
        @Part("startingTime") startingTime: RequestBody,
        @Part("carModel") carModel: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<CommonResponse>

    @Multipart
    @POST("/api/ambulance/add")
    suspend fun addAmbulance(
        @Part("driverName") driverName: RequestBody,
        @Part("address") address: RequestBody,
        @Part("contributorId") contributorId: RequestBody,
        @Part("ambulanceRegNo") ambulanceRegNo: RequestBody,
        @Part("others") others: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<CommonResponse>


    //blood Bank
   // @POST("/api/bloodbank/add , all, all/group")


    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): Api {
            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()
            return Retrofit.Builder().client(okHttpClient).baseUrl(
                BASEURL
            ).addConverterFactory(GsonConverterFactory.create()).build()
                .create(Api::class.java)
        }
    }


}