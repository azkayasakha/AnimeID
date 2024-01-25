package com.mobiledevidn.animeid.data.remote.respon.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("pagination")
    val pagination: Pagination
)