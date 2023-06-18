package com.dqapi.data.bestiario

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Sorts
import org.litote.kmongo.*

class MonstruoDataSourceImpl(
    db: MongoDatabase
) : MonstruoDataSource {
    private val monstruos = db.getCollection<Monstruo>("monstruos")
    private val familias = db.getCollection<Familia>("familias")
    private val juegos = db.getCollection<Juego>("juegos")

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

    override suspend fun actualizarMonstruo(idLista: String, monstruo: Monstruo): Boolean {
        val updateResult = monstruos.updateOne(
            Monstruo::idLista eq idLista,
            set(
                Monstruo::nombre setTo monstruo.nombre,
                Monstruo::imagen setTo monstruo.imagen,
                Monstruo::familia setTo monstruo.familia,
                Monstruo::atributos setTo monstruo.atributos
            )
        )
        return updateResult.modifiedCount > 0
    }

    override suspend fun borrarMonstruo(idLista: String): Boolean {
        // Es muy probable que el admin se equivoque de id y este sea un caso para borrar
        // el monstruo en lugar de editarlo. Por si acaso, sólo borramos uno.
        return monstruos.deleteOne(Monstruo::idLista eq idLista).wasAcknowledged()
    }

    override suspend fun getTodosMonstruos(orden: String?, tipo: String?): List<Monstruo> {
        val query = if (orden != null) {
            when (tipo) {
                "Ascendente" -> {
                    monstruos.find().sort(Sorts.ascending(orden))
                }

                "Descendente" -> {
                    monstruos.find().sort(Sorts.descending(orden))
                }

                else -> {
                    // Por defecto, Ascendente.
                    monstruos.find().sort(Sorts.ascending(orden))
                }
            }
        } else {
            monstruos.find()
        }
        return query.toList()
    }

    override suspend fun getTodasFamilias(): List<Familia> {
        return familias.find().toList()
    }

    override suspend fun getTodosJuegos(): List<Juego> {
        return juegos.find().toList()
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
        return monstruos.find(Monstruo::atributos.elemMatch(Atributo::juego eq abr)).toList()
    }
}