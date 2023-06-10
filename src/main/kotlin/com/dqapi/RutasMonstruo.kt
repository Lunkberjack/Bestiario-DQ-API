package com.dqapi

import com.dqapi.data.monstruo.Monstruo
import com.dqapi.data.monstruo.MonstruoDataSource
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