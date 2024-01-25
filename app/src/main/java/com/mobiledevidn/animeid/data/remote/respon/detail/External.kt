package com.mobiledevidn.animeid.data.remote.respon.detail


import com.google.gson.annotations.SerializedName

data class External(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)