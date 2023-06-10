package com.dqapi.data.respuestas

import kotlinx.serialization.Serializable

@Serializable
data class RespuestaMonstruo(
    val idLista: String,
    val nombre: String,
    val imagen: String
)