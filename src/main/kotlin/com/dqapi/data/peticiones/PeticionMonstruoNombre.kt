package com.dqapi.data.peticiones

import kotlinx.serialization.Serializable

@Serializable
data class PeticionMonstruoNombre(
    val nombre: String,
)