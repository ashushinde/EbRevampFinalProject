package com.palm.tatarewamp.retrofit

import com.google.gson.JsonObject
import com.palm.newbenefit.ApiConfig.LoginResponseNew
import com.palm.tatarewamp.CityResponse
import com.palm.tatarewamp.StateResponse

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("admin/login")
    @FormUrlEncoded
    fun getLoginDetails(
        @Field("email") employee_email: String,
        @Field("password") employee_pwd: String
        // @Field("mobReq") mobReq: String
    ): Call<LoginResponseNew>


    @GET("admin/get/state")
    //  fun getStateList(@Header("Authorization")token:String?):Call<StateResponse>
    fun getStateList(@Header("Authorization")token:String?):Call<StateResponse>



    @POST("admin/get/city")
    fun getCityList(@Body jsonObject: JsonObject,
                    @Header("Authorization")token:String?
    ):Call<CityResponse>


}