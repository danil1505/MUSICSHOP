package com.guitarstore.model

import com.google.gson.annotations.SerializedName

data class Guitar(
    @SerializedName("id")           val id:           String,
    @SerializedName("name")         val name:         String,
    @SerializedName("brand")        val brandId:      Int,
    @SerializedName("brand_name")   val brandName:    String,
    @SerializedName("category")     val categoryId:   Int,
    @SerializedName("category_name") val categoryName: String,
    @SerializedName("price")        val price:        Double,
    @SerializedName("description")  val description:  String?,
    @SerializedName("image_url")    val imageUrl:     String?,
    @SerializedName("in_stock")     val inStock:      Boolean,
    @SerializedName("created_at")   val createdAt:    String?
)

data class Brand(
    @SerializedName("id")   val id:   Int,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String?,
    @SerializedName("founded_year") val foundedYear: Int?,
    @SerializedName("description") val description: String?
) { override fun toString() = name }

data class Category(
    @SerializedName("id")   val id:   Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?
) { override fun toString() = name }

// Django REST Framework pagination format
data class PageResponse<T>(
    @SerializedName("count")    val count:    Int,
    @SerializedName("next")     val next:     String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results")  val results:  List<T>
) {
    val totalElements: Long get() = count.toLong()
    val totalPages: Int get() = ((count + 19) / 20).coerceAtLeast(1)
    val last: Boolean get() = next == null
}

data class GuitarRequest(
    val name:        String,
    val brand:       Int,
    val category:    Int,
    val price:       Double,
    val description: String?,
    val image_url:   String?,
    val in_stock:    Boolean
)
