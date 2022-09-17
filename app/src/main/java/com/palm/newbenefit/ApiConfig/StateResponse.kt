package com.palm.tatarewamp

import com.google.gson.annotations.SerializedName

class StateResponse {
    @SerializedName("status")
    var status: Boolean? = null


    @SerializedName("data")
    var data: ArrayList<StateList>? = null

}