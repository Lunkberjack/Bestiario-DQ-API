package com.dqapi.data.peticiones

import kotlinx.serialization.Serializable

/**
 * Se enviará como JSON a algunas peticiones POST.
 */
@Serializable
data class PeticionAuth(
    val username: String,
    val pass: String
)