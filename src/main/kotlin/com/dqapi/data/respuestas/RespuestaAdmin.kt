package com.dqapi.data.respuestas

import kotlinx.serialization.Serializable

@Serializable
data class RespuestaAdmin(
    val admin: Boolean
)