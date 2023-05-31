package com.dqapi.plugins

import com.dqapi.autentificar
import com.dqapi.data.usuario.UsuarioDataSource
import com.dqapi.getInfoSecreta
import com.dqapi.login
import com.dqapi.registro
import com.dqapi.security.hash.HashService
import com.dqapi.security.jwt.JwtTokenConfig
import com.dqapi.security.jwt.JwtTokenService
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureRouting(
    usuarioDataSource: UsuarioDataSource,
    hashServicio: HashService,
    tokenServicio: JwtTokenService,
    tokenConf: JwtTokenConfig
) {
    // Debemos llamar a las rutas personalizadas desde este bloque routing.
    routing {
        registro(hashServicio, usuarioDataSource)
        login(usuarioDataSource, hashServicio, tokenServicio, tokenConf)
        autentificar()
        getInfoSecreta()
    }
}
