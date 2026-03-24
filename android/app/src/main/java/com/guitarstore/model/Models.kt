package com.guitarstore.model

import com.google.gson.annotations.SerializedName

data class Guitar(
    @SerializedName("id")           val id:           String,
    @SerializedName("name")         val name:         String,
    @SerializedName("brandId")      val brandId:      Int,
    @SerializedName("brandName")    val brandName:    String,
    @SerializedName("categoryId")   val categoryId:   Int,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("price")        val price:        Double,
    @SerializedName("description")  val description:  String?,
    @SerializedName("imageUrl")     val imageUrl:     String?,
    @SerializedName("inStock")      val inStock:      Boolean,
    @SerializedName("createdAt")    val createdAt:    String?
)

data class Brand(
    @SerializedName("id")   val id:   Int,
    @SerializedName("name") val name: String
) { override fun toString() = name }

data class Category(
    @SerializedName("id")   val id:   Int,
    @SerializedName("name") val name: String
) { override fun toString() = name }

// Nested page info from Spring Boot's VIA_DTO serialization
data class PageInfo(
    @SerializedName("size")          val size:          Int,
    @SerializedName("number")        val number:        Int,
    @SerializedName("totalElements") val totalElements: Long,
    @SerializedName("totalPages")    val totalPages:    Int
)

data class PageResponse<T>(
    @SerializedName("content") val content: List<T>,
    @SerializedName("page")    val page:    PageInfo?
) {
    val totalElements: Long get() = page?.totalElements ?: content.size.toLong()
    val totalPages:    Int  get() = page?.totalPages    ?: 1
    val number:        Int  get() = page?.number        ?: 0
    val last:          Boolean get() = number >= totalPages - 1
}

data class GuitarRequest(
    @SerializedName("name")        val name:        String,
    @SerializedName("brandId")     val brandId:     Int,
    @SerializedName("categoryId")  val categoryId:  Int,
    @SerializedName("price")       val price:       Double,
    @SerializedName("description") val description: String?,
    @SerializedName("imageUrl")    val imageUrl:    String?,
    @SerializedName("inStock")     val inStock:     Boolean
)
