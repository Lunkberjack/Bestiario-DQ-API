package com.dqapi.data.monstruo

interface MonstruoDataSource {
    // Puede devolver un Usuario o un nulo.
    suspend fun getMonstruoNombre(nombre: String): Monstruo?
    // Devuelve true si se insertó y false si no.
    suspend fun aniadirMonstruo(monstruo: Monstruo): Boolean
    // Devuelve todos los monstruos en la colección.
    suspend fun getTodosMonstruos(): List<Monstruo>
}