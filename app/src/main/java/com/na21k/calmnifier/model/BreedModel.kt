package com.na21k.calmnifier.model

import java.io.Serializable

data class BreedModel(
    val id: String,
    val name: String,
    val description: String,
    val temperament: String,
    val origin: String,
    val weight: BreedWeightModel,
    val lifeSpan: String,
    val referenceImageId: String?,
) : Serializable {
    data class BreedWeightModel(
        val metric: String,
        val imperial: String,
    ) : Serializable
}
