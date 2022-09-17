package com.palm.tatarewamp

import com.google.gson.annotations.SerializedName

class CityResponse {

    @SerializedName("status")
    var status: Boolean? = null


    @SerializedName("data")
    var data: ArrayList<CityList>? = null

}