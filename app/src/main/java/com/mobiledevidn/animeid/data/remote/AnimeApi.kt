package com.mobiledevidn.animeid.data.remote

import com.mobiledevidn.animeid.data.remote.respon.detail.DetailResponse
import com.mobiledevidn.animeid.data.remote.respon.search.SearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query : String
    ) : SearchResponse

    @GET("anime/{id}/full")
    suspend fun detailAnime(
        @Path(value = "id") id : String
    ) : DetailResponse

    companion object {
        private const val BASE_URL = "https://api.jikan.moe/v4/"

        fun create() : AnimeApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AnimeApi::class.java)
        }
    }

}