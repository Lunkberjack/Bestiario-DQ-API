package com.dqapi.data.respuestas

import kotlinx.serialization.Serializable

@Serializable
data class MonstruoBusqueda(
    val idLista: String,
    var nombre: String,
)