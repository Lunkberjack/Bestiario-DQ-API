package com.dqapi.data.bestiario

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Juego(
    val abr: String,
    val anio: String,
    val descripcion: String,
    val nombre: String,
    @BsonId
    val id: String = ObjectId().toString()
)