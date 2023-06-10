package com.dqapi.data.monstruo

interface MonstruoDataSource {
    // Puede devolver un Monstruo o un nulo.
    suspend fun getMonstruoIdLista(idLista: String): Monstruo?
    // Puede devolver un Monstruo o un nulo a partir de su nombre (único).
    suspend fun getMonstruoNombre(nombre: String): Monstruo?
    // Devuelve true si se insertó y false si no.
    suspend fun aniadirMonstruo(monstruo: Monstruo): Boolean
    // Devuelve todos los monstruos en la colección.
    suspend fun getTodosMonstruos(): List<Monstruo>
}