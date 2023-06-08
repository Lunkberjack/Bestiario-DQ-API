package com.dqapi.plugins

import com.dqapi.*
import com.dqapi.data.monstruo.MonstruoDataSource
import com.dqapi.data.usuario.UsuarioDataSource
import com.dqapi.security.hash.HashService
import com.dqapi.security.jwt.JwtTokenConfig
import com.dqapi.security.jwt.JwtTokenService
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(
    usuarioDataSource: UsuarioDataSource,
    hashServicio: HashService,
    tokenServicio: JwtTokenService,
    tokenConf: JwtTokenConfig,
    monstruoDataSource: MonstruoDataSource
) {
    // Debemos llamar a las rutas personalizadas desde este bloque routing.
    routing {
        registro(hashServicio, usuarioDataSource)
        login(usuarioDataSource, hashServicio, tokenServicio, tokenConf)
        autentificar()
        getInfoSecreta()
        prueba()
        // Monstruo
        newMonstruo(monstruoDataSource)
        getMonstruos(monstruoDataSource)
    }
}
