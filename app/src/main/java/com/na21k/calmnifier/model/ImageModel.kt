package com.na21k.calmnifier.model

data class ImageModel(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<BreedModel>,
    val favorite: FavoriteModel?,
)
