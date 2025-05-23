package com.example.tugasmobile2

import retrofit2.http.GET

interface ApiService {
    @GET("/data")
    suspend fun getData(): DataModel
}
