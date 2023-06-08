package com.dqapi.data.monstruo

import com.dqapi.data.usuario.Usuario
import com.dqapi.data.usuario.UsuarioDataSource
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class MonstruoDataSourceImpl(
    db: MongoDatabase
): MonstruoDataSource {
    val monstruos = db.getCollection<Monstruo>("monstruos")

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
}