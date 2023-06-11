package com.dqapi.data.bestiario

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Monstruo(
    val idLista: String,
    val nombre: String,
    val imagen: String,
    val familia: String,
    val atributos: List<Atributo>,
    @BsonId
    val id: String = ObjectId().toString()
)