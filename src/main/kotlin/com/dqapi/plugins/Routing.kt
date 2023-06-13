package com.dqapi.plugins

import com.dqapi.*
import com.dqapi.data.bestiario.MonstruoDataSource
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

        // Monstruo
        getMonstruoIdLista(monstruoDataSource)
        getMonstruoNombre(monstruoDataSource)
        newMonstruo(monstruoDataSource)
        getMonstruos(monstruoDataSource)

        // Juego
        getJuegoAbr(monstruoDataSource)
        getJuegos(monstruoDataSource)

        // Familia
        getFamiliaNombre(monstruoDataSource)
        getFamilias(monstruoDataSource)

        // Filtro
        filtroFamilia(monstruoDataSource)
        filtroJuego(monstruoDataSource)
    }
}
