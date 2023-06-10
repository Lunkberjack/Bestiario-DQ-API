package com.dqapi.data.peticiones

import kotlinx.serialization.Serializable

@Serializable
data class PeticionMonstruoPost(
    val idLista: String,
    val nombre: String,
    val imagen: String
)