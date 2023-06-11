package com.dqapi.data.bestiario

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Familia(
    val descripcion: String,
    val imagen: String?,
    val nombre: String,
    @BsonId
    val id: String = ObjectId().toString()
)