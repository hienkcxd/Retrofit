package com.example.retrofitdemo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumService {
    @GET(value = "/albums")
    suspend fun getAlbum() : Response<Album>

    @GET(value = "/albums")
    suspend fun getAlbumByUserId(@Query("userId") userId : Int):Response<Album>

    @GET(value = "/albums/{id}")
    suspend fun getAlbumById(@Path(value = "id") albumId : Int):Response<AlbumItem>
}