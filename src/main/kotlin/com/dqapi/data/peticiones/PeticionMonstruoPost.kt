package com.dqapi.data.peticiones

import com.dqapi.data.bestiario.Atributo
import kotlinx.serialization.Serializable

@Serializable
data class PeticionMonstruoPost(
    val idLista: String,
    val nombre: String,
    val imagen: String,
    val familia: String,
    val atributos: List<Atributo>
)