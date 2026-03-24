package com.guitarstore.api

import com.guitarstore.model.*
import retrofit2.Response
import retrofit2.http.*

interface GuitarApi {

    @GET("guitars")
    suspend fun getGuitars(
        @Query("search")   search:   String?  = null,
        @Query("brand")    brand:    Int?     = null,
        @Query("category") category: Int?     = null,
        @Query("minPrice") minPrice: Double?  = null,
        @Query("maxPrice") maxPrice: Double?  = null,
        @Query("page")     page:     Int      = 0,
        @Query("size")     size:     Int      = 20
    ): Response<PageResponse<Guitar>>

    @GET("guitars/{id}")
    suspend fun getGuitar(@Path("id") id: String): Response<Guitar>

    @POST("guitars")
    suspend fun createGuitar(@Body request: GuitarRequest): Response<Guitar>

    @PUT("guitars/{id}")
    suspend fun updateGuitar(@Path("id") id: String, @Body request: GuitarRequest): Response<Guitar>

    @DELETE("guitars/{id}")
    suspend fun deleteGuitar(@Path("id") id: String): Response<Void>

    @GET("brands")
    suspend fun getBrands(): Response<List<Brand>>

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>
}
