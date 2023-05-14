package com.dqapi

import com.dqapi.data.usuario.Usuario
import com.dqapi.data.usuario.UsuarioDataSourceImpl
import io.ktor.server.application.*
import com.dqapi.plugins.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.KMongo

fun main(args: Array<String>): Unit =
        io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val mongoPass = System.getenv("MONGO_PW")
    val dbName = "dragon-quest-api"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://Lunkberjack:$mongoPass@cluster0.kuelx0l.mongodb.net/$dbName?retryWrites=true&w=majority"
    ).getDatabase(dbName)

    val usuarioDataSource = UsuarioDataSourceImpl(db)

    GlobalScope.launch {
        val usuario = Usuario(
            username = "prueba",
            pass = "prueba",
            salt = "salt"
        )
        usuarioDataSource.aniadirUsuario(usuario)
    }

    configureSecurity()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
