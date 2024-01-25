package com.mobiledevidn.animeid.data.remote.respon.detail


import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("data")
    val `data`: Data
)