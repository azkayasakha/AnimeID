package com.mobiledevidn.animeid.data.remote.respon.detail


import com.google.gson.annotations.SerializedName

data class To(
    @SerializedName("day")
    val day: Any,
    @SerializedName("month")
    val month: Any,
    @SerializedName("year")
    val year: Any
)