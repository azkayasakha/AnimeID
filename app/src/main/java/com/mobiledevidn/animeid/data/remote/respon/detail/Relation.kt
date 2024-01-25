package com.mobiledevidn.animeid.data.remote.respon.detail


import com.google.gson.annotations.SerializedName

data class Relation(
    @SerializedName("entry")
    val entry: List<Entry>,
    @SerializedName("relation")
    val relation: String
)