package com.dqapi.data.bestiario

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Monstruo(
    val idLista: String,
    var nombre: String,
    var imagen: String?,
    var familia: String?,
    var atributos: List<Atributo>?,
    @BsonId
    val id: String? = ObjectId().toString()
)