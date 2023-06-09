package com.dqapi.data.usuario

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Usuario (
    val username: String,
    val pass: String,
    val sal: String,
    val admin: Boolean = false,
    @BsonId
    val id: ObjectId = ObjectId()
)
