package com.dqapi

import com.dqapi.data.bestiario.Familia
import com.dqapi.data.bestiario.Juego
import com.dqapi.data.bestiario.Monstruo
import com.dqapi.data.bestiario.MonstruoDataSource
import com.dqapi.data.peticiones.PeticionMonstruoPost
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.newMonstruo(
    monstruoDataSource: MonstruoDataSource
) {
    post("/new-monstruo") {
        val peticion = call.receiveNullable<PeticionMonstruoPost>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val enBlanco = peticion.imagen.isBlank() || peticion.nombre.isBlank()

        // Devolvemos un mensaje de conflicto si alguno de estos campos no se ha incluido.
        if (enBlanco) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val monstruo = Monstruo(
            idLista = peticion.idLista,
            nombre = peticion.nombre,
            imagen = peticion.imagen,
            familia = peticion.familia,
            atributos = peticion.atributos
        )

        val realizado = monstruoDataSource.aniadirMonstruo(monstruo)
        if (!realizado) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        // Si la inserción fue realizada con éxito se devuelve un status OK.
        call.respond(HttpStatusCode.OK)
    }
}

/**
 * Devuelve el id del usuario que hace la petición.
 */
fun Route.getMonstruos(
    monstruoDataSource: MonstruoDataSource
) {
    get("/monstruos") {
        val lista: List<Monstruo> = monstruoDataSource.getTodosMonstruos()
        lista.let {
            call.respond(
                HttpStatusCode.OK,
                lista // Devuelve la lista completa
            )
        }
    }
}

fun Route.getFamilias(
    monstruoDataSource: MonstruoDataSource
) {
    get("/familias") {
        val lista: List<Familia> = monstruoDataSource.getTodasFamilias()
        lista.let {
            call.respond(
                HttpStatusCode.OK,
                lista // Devuelve la lista completa
            )
        }
    }
}

fun Route.getJuegos(
    monstruoDataSource: MonstruoDataSource
) {
    get("/juegos") {
        val lista: List<Juego> = monstruoDataSource.getTodosJuegos()
        lista.let {
            call.respond(
                HttpStatusCode.OK,
                lista // Devuelve la lista completa
            )
        }
    }
}


fun Route.getMonstruoIdLista(
    monstruoDataSource: MonstruoDataSource
) {
    get("/monstruo/id/{idLista}") {
        val idLista = call.parameters["idLista"]
        val monstruo = idLista?.let { it1 -> monstruoDataSource.getMonstruoIdLista(it1) }
        monstruo?.let {
            call.respond(
                HttpStatusCode.OK,
                monstruo
            )
        } ?: call.respond(
            HttpStatusCode.OK,
            "No hay ningún monstruo con ese id :("
        )
    }
}

fun Route.getMonstruoNombre(
    monstruoDataSource: MonstruoDataSource
) {
    get("/monstruo/nombre/{nombre}") {
        val monstruoNombre = call.parameters["nombre"]
        val monstruo = monstruoNombre?.let { it1 -> monstruoDataSource.getMonstruoNombre(it1) }
        monstruo?.let {
            call.respond(
                HttpStatusCode.OK,
                monstruo
            )
        } ?: call.respond(
            HttpStatusCode.OK,
            "No hay ningún monstruo con ese nombre :("
        )
    }
}

fun Route.getFamiliaNombre(
    monstruoDataSource: MonstruoDataSource
) {
    get("/monstruo/familia/{nombre}") {
        val familiaNombre = call.parameters["nombre"]
        val familia = familiaNombre?.let { it1 -> monstruoDataSource.getFamiliaNombre(it1) }
        familia?.let {
            call.respond(
                HttpStatusCode.OK,
                familia
            )
        } ?: call.respond(
            HttpStatusCode.OK,
            "No hay ningún monstruo con ese nombre :("
        )
    }
}

fun Route.getJuegoAbr(
    monstruoDataSource: MonstruoDataSource
) {
    get("/monstruo/juego/{abr}") {
        val juegoAbr = call.parameters["abr"]
        val juego = juegoAbr?.let { it1 -> monstruoDataSource.getJuegoNombre(it1) }
        juego?.let {
            call.respond(
                HttpStatusCode.OK,
                juego
            )
        } ?: call.respond(
            HttpStatusCode.OK,
            "No hay ningún monstruo con ese nombre :("
        )
    }
}

fun Route.filtroFamilia(
    monstruoDataSource: MonstruoDataSource
) {
    get("/monstruos/{familia}") {
        val familia = call.parameters["familia"]
        if (familia != null) {
            val lista: List<Monstruo> = monstruoDataSource.filtroFamilia(familia)
            call.respond(HttpStatusCode.OK, lista)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Familia no especificada")
        }
    }
}

fun Route.filtroJuego(
    monstruoDataSource: MonstruoDataSource
) {
    get("/monstruos/{juego}") {
        val juego = call.parameters["juego"]
        if (juego != null) {
            val lista: List<Monstruo> = monstruoDataSource.filtroJuego(juego)
            call.respond(HttpStatusCode.OK, lista)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Juego no especificadoa")
        }
    }
}