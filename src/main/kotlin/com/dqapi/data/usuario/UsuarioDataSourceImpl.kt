package com.dqapi.data.usuario

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class UsuarioDataSourceImpl(
    db: MongoDatabase
): UsuarioDataSource {
    val usuarios = db.getCollection<Usuario>()

    override suspend fun getUsuarioNombre(username: String): Usuario? {
        // Encontramos un usuario en la db que tenga el mismo nombre que el que
        // hemos pasado como parámetro.
        return usuarios.findOne(Usuario::username eq username)
    }

    override suspend fun aniadirUsuario(usuario: Usuario): Boolean {
        // Si se insertó con éxito, wasAcknowledged() será true.
        return usuarios.insertOne(usuario).wasAcknowledged()
    }
}