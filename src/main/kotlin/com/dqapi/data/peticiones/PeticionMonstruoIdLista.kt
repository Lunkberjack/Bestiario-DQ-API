package com.dqapi.data.peticiones

import kotlinx.serialization.Serializable

@Serializable
data class PeticionMonstruoIdLista(
    val idLista: String,
    val nombre: String,
)