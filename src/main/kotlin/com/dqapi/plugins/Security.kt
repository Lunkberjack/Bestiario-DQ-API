package com.dqapi.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dqapi.security.jwt.JwtTokenConfig
import io.ktor.server.application.*

fun Application.configureSecurity(conf: JwtTokenConfig) {

    authentication {
        jwt {
            val jwtAudience = this@configureSecurity.environment.config.property("jwt.audience").getString()
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(conf.secret))
                    .withAudience(conf.audience)
                    .withIssuer(conf.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(conf.audience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}