package com.dqapi.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtTokenService: TokenService {
    override fun generarToken(conf: JwtTokenConfig, vararg claims: JwtTokenClaim): String {
        var token = JWT.create()
            .withAudience(conf.audience)
            .withIssuer(conf.issuer)
            // Sumamos el tiempo de expiración al momento en que se crea el token.
            .withExpiresAt(Date(System.currentTimeMillis() + conf.expiration))

        claims.forEach { claim ->
            token = token.withClaim(claim.nombre, claim.valor)
        }

        // Devolvemos el token tras hashearlo (aún tenemos que añadirle una sal,
        // para asegurarnos de que la seguridad es suficiente).
        return token.sign(Algorithm.HMAC256(conf.secret))
    }
}