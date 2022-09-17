package com.palm.newbenefit.ApiConfig

import com.google.gson.annotations.SerializedName

class EmailError {
    @SerializedName("email")
    var emailErrorList: ArrayList<String>? = null
}