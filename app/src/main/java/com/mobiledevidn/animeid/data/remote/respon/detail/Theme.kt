package com.mobiledevidn.animeid.data.remote.respon.detail


import com.google.gson.annotations.SerializedName

data class Theme(
    @SerializedName("endings")
    val endings: List<String>,
    @SerializedName("openings")
    val openings: List<String>
)