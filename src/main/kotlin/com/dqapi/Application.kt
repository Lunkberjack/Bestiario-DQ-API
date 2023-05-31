package com.dqapi

import com.dqapi.data.usuario.Usuario
import com.dqapi.data.usuario.UsuarioDataSourceImpl
import io.ktor.server.application.*
import com.dqapi.plugins.*
import com.dqapi.security.hash.HashServicioImpl
import com.dqapi.security.jwt.JwtTokenConfig
import com.dqapi.security.jwt.JwtTokenService
import io.ktor.util.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.KMongo

fun main(args: Array<String>): Unit =
        io.ktor.server.netty.EngineMain.main(args)

@OptIn(DelicateCoroutinesApi::class)
@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val mongoPass = System.getenv("MONGO_PW")
    val dbName = "dragon-quest-api"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://Lunkberjack:$mongoPass@cluster0.kuelx0l.mongodb.net/$dbName?retryWrites=true&w=majority"
    ).getDatabase(dbName)

    val usuarioDataSource = UsuarioDataSourceImpl(db)
    val tokenServicio = JwtTokenService()

    // En el archivo de configuración application.conf tenemos que definir estas propiedades.
    val tokenConf = JwtTokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        // Un año en milisegundos.
        expiration = 365L * 1000L * 60L * 60L * 24L,
        // Este secreto se define en las Run Configurations.
        secret = System.getenv("JWT_SECRET")
    )

    val hashServicio = HashServicioImpl()

    /*
    GlobalScope.launch {
        val usuario = Usuario(
            username = "prueba2",
            pass = "prueba2",
            sal = "sal"
        )
        usuarioDataSource.aniadirUsuario(usuario)
    }
    */

    // Pasamos una instancia de la clase configuración al método que
    // tendrá en cuenta dicha configuración para la seguridad.
    configureSecurity(tokenConf)
    configureRouting(usuarioDataSource, hashServicio, tokenServicio, tokenConf)
    configureSerialization()
    configureMonitoring()
}
