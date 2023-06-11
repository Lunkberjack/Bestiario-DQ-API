package com.dqapi.data.bestiario

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.elemMatch
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class MonstruoDataSourceImpl(
    db: MongoDatabase
): MonstruoDataSource {
    val monstruos = db.getCollection<Monstruo>("monstruos")
    val familias = db.getCollection<Familia>("familias")
    val juegos = db.getCollection<Juego>("juegos")

    override suspend fun getMonstruoIdLista(idLista: String): Monstruo? {
        // Encontramos un monstruo en la db que tenga el mismo id que el que
        // hemos pasado como parámetro.
        return monstruos.findOne(Monstruo::idLista eq idLista)
    }

    override suspend fun getMonstruoNombre(nombre: String): Monstruo? {
        // Encontramos un monstruo en la db que tenga el mismo nombre que el que
        // hemos pasado como parámetro.
        return monstruos.findOne(Monstruo::nombre eq nombre)
    }

    override suspend fun aniadirMonstruo(monstruo: Monstruo): Boolean {
        // Si se insertó con éxito, wasAcknowledged() será true.
        return monstruos.insertOne(monstruo).wasAcknowledged()
    }

    override suspend fun getTodosMonstruos(): List<Monstruo> {
        return monstruos.find().toList()
    }

    override suspend fun getFamiliaNombre(nombre: String): Familia? {
        return familias.findOne(Familia::nombre eq nombre)
    }

    override suspend fun getJuegoNombre(abr: String): Juego? {
        return juegos.findOne(Juego::abr eq abr)
    }

    override suspend fun filtroFamilia(nombre: String): List<Monstruo> {
        val familia = familias.findOne(Familia::nombre eq nombre)
        return if (familia != null) {
            monstruos.find(Monstruo::familia eq familia.nombre).toList()
        } else {
            emptyList()
        }
    }

    override suspend fun filtroJuego(abr: String): List<Monstruo> {
        val juego = juegos.findOne(Juego::abr eq abr)
        return if (juego != null) {
            return monstruos.find(Monstruo::atributos.elemMatch(Juego::abr eq abr)).toList()
        } else {
            emptyList()
        }
    }
}