package com.dqapi.data.peticiones

import kotlinx.serialization.Serializable

@Serializable
data class PeticionMonstruo(
    val nombre: String,
    val imagen: String
)