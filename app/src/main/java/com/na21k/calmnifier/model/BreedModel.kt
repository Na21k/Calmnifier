package com.na21k.calmnifier.model

data class BreedModel(
    val id: String,
    val name: String,
    val description: String,
    val temperament: String,
    val origin: String,
    val weight: BreedWeightModel,
    val lifeSpan: String,
    val referenceImageId: String?,
) {
    data class BreedWeightModel(
        val metric: String,
        val imperial: String,
    )
}
