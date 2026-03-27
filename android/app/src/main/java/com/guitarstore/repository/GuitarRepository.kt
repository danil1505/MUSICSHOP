package com.guitarstore.repository

import com.guitarstore.api.RetrofitClient
import com.guitarstore.model.*

class GuitarRepository {

    private val api = RetrofitClient.api

    suspend fun getGuitars(
        search: String? = null, brand: Int? = null, category: Int? = null,
        minPrice: Double? = null, maxPrice: Double? = null, page: Int = 1
    ) = api.getGuitars(search, brand, category, minPrice, maxPrice, page, 20)

    suspend fun getGuitar(id: String)                        = api.getGuitar(id)
    suspend fun createGuitar(req: GuitarRequest)             = api.createGuitar(req)
    suspend fun updateGuitar(id: String, req: GuitarRequest) = api.updateGuitar(id, req)
    suspend fun deleteGuitar(id: String)                     = api.deleteGuitar(id)
    suspend fun getBrands()                                  = api.getBrands()
    suspend fun getCategories()                              = api.getCategories()
}
