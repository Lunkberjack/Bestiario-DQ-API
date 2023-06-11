package com.dqapi.data.bestiario

import kotlinx.serialization.Serializable

@Serializable
data class Atributo(
    val experiencia: Int,
    val juego: String,
    val lugares: List<String>,
    val objetos: List<String>,
    val oro: Int
)