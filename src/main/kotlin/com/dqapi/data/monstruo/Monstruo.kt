package com.dqapi.data.monstruo

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Monstruo (
    val nombre: String,
    val imagen: String,
    @BsonId
    val id: String = ObjectId().toString()
)
