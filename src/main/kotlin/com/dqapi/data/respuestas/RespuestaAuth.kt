package com.dqapi.data.respuestas

import kotlinx.serialization.Serializable

/**
 * Respuesta que el usuario recibirá del servidor (un token)
 * si su registro ha sido verificado y es válido.
 */
@Serializable
data class RespuestaAuth(
    val token: String,
    val username: String,
    val admin: Boolean
)