package com.palm.tatarewamp.SslData

import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("status")
    var status: String? = null



    @SerializedName("message")
    var message: String? = null

    @SerializedName("api_token")
    var api_token: String? = null

    @SerializedName("modules")
    var modules: String? = null




}