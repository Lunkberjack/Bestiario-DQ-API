package com.dqapi

import com.dqapi.data.peticiones.PeticionAuth
import com.dqapi.data.respuestas.RespuestaAuth
import com.dqapi.data.usuario.Usuario
import com.dqapi.data.usuario.UsuarioDataSource
import com.dqapi.security.hash.HashConSal
import com.dqapi.security.hash.HashService
import com.dqapi.security.jwt.JwtTokenClaim
import com.dqapi.security.jwt.JwtTokenConfig
import com.dqapi.security.jwt.JwtTokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.codec.digest.DigestUtils

fun Route.registro(
    hashServicio: HashService,
    usuarioDataSource: UsuarioDataSource
) {
    post("registro") {
        val peticion = call.receiveNullable<PeticionAuth>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val enBlanco = peticion.username.isBlank() || peticion.pass.isBlank()
        val passCorta = peticion.pass.length < 8

        // Devolvemos un mensaje de conflicto si alguna de estas validaciones
        // da problemas.
        if(enBlanco || passCorta) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val hashConSal = hashServicio.generarHashConSal(peticion.pass)
        val usuario = Usuario(
            username = peticion.username,
            pass = hashConSal.hash,
            sal = hashConSal.sal
        )

        val realizado = usuarioDataSource.aniadirUsuario(usuario)
        if(!realizado) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        // Si el registro fue realizado con éxito se devuelve un status OK.
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.login(
    usuarioDataSource: UsuarioDataSource,
    hashServicio: HashService,
    tokenServicio: JwtTokenService,
    tokenConf: JwtTokenConfig
) {
    post("login") {
        val peticion = call.receiveNullable<PeticionAuth>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val usuario = usuarioDataSource.getUsuarioNombre(peticion.username)
        // Si el usuario no se encontró en la base de datos:
        if(usuario == null) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        // Si el usuario existe (aún no sabemos si la contraseña es correcta):
        val passCorrecta = hashServicio.verificar(
            valor = peticion.pass,
            hashConSal = HashConSal(
                hash = usuario.pass,
                sal = usuario.sal
            )
        )

        // Si las contraseñas no coinciden:
        if (!passCorrecta) {
            println("Usuario pass: ${usuario.pass}")
            println("Petición hash: $")
            //println("Entered hash: ${DigestUtils.sha256Hex("${usuario.sal}${peticion.pass}")}, Hashed PW: ${usuario.pass}")
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        //Entered hash: 945b96228e55f31439032adab3d9ffdb8ed164ad6955f504cb7ffae2b0a1ba1d, Hashed PW: 327ccd2a1be44b4849614d220d345a7d2c8ae07820e59272dd340b8d98b1a4097a045d539f042c92ff7f8315ad9f8d130b0b2e8199fad4d58e3857d2ad742a03
        // Si coinciden:
        val token = tokenServicio.generarToken(
            conf = tokenConf,
            JwtTokenClaim(
                nombre = "usuarioId",
                valor = usuario.id.toString()
            )
        )

        // Si todo ha ido bien devolvemos un estado OK.
        call.respond(
            status = HttpStatusCode.OK,
            message = RespuestaAuth(
                token = token,
                admin = usuario.admin
            )
        )
    }
}

/**
 * Para que el usuario no tenga que volver a hacer el login cada vez que entre
 * a la app (sabiendo que el token aún es válido).
 *
 * Este método se llamará al iniciar la app y pasará desapercibido mediante la
 * splash screen.
 */
fun Route.autentificar() {
    // Si hay un token válido, el bloque authenticate comprobará automáticamente
    // (usando el mecanismo de autentificación por defecto) que la sesión es válida.
    authenticate {
        get("autentificar") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

/**
 * Devuelve el id del usuario que hace la petición.
 */
fun Route.getInfoSecreta() {
    // Sólo para usuarios autentificados.
    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val usuarioId = principal?.getClaim("usuarioId", String::class)
            call.respond(HttpStatusCode.OK, "Tu id de usuario es $usuarioId")
        }
    }
}

fun Route.esAdmin() {
    // Sólo para usuarios autentificados.
    authenticate {
        get("admin") {

        }
    }
}