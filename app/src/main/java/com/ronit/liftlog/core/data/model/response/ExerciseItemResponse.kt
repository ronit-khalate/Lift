package com.ronit.liftlog.core.data.model.response

data class ExerciseItemResponse(
    val category: String,
    val equipment: String,
    val force: String,
    val id: String,
    val images: List<String>,
    val instructions: List<String>,
    val level: String,
    val mechanic: String,
    val name: String,
    val primaryMuscles: List<String>,
    val secondaryMuscles: List<String>
)