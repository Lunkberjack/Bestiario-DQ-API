package com.dqapi.security.jwt

data class JwtTokenConfig(
    val issuer: String,
    val secret: String,
    val expiration: Long,
    val audience: String
)