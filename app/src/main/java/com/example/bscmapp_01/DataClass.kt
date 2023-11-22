package com.example.bscmapp_01

import com.google.gson.annotations.SerializedName

data class DataClass(
    @SerializedName("id") val id: Int,
    @SerializedName("serial") val serial: String?,
    @SerializedName("smrValue") val smrValue: String?
)
