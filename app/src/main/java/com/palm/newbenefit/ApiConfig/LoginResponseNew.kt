package com.palm.newbenefit.ApiConfig

import com.google.gson.annotations.SerializedName

class LoginResponseNew {

    @SerializedName("status")
    var status: Boolean? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("api_token")
    var api_token: String? = null

    @SerializedName("errors")
    var emailError :EmailError?=null


}