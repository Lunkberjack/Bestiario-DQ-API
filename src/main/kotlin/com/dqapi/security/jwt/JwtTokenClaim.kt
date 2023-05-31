package com.dqapi.security.jwt

data class JwtTokenClaim(
    // Usados para almacenar valores del token.
    val nombre: String,
    val valor: String
)
