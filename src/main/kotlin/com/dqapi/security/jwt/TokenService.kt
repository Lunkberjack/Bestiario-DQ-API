package com.dqapi.security.jwt

/**
 * Sólo provee funcionalidad a otras clases, por eso es un servicio
 * (en este caso, a JwtTokenService, donde se implementan estos métodos).
 */
interface TokenService {
    fun generarToken(
        conf: JwtTokenConfig,
        vararg claims: JwtTokenClaim
    ): String
}