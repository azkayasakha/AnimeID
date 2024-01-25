package com.mobiledevidn.animeid.data.remote.respon.search


import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)