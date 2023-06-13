package com.dqapi.data.bestiario

interface MonstruoDataSource {
    // Puede devolver un Monstruo o un nulo.
    suspend fun getMonstruoIdLista(idLista: String): Monstruo?

    // Puede devolver un Monstruo o un nulo a partir de su nombre (único).
    suspend fun getMonstruoNombre(nombre: String): Monstruo?

    // Devuelve true si se insertó y false si no.
    suspend fun aniadirMonstruo(monstruo: Monstruo): Boolean

    // Devuelve todos los monstruos en la colección.
    suspend fun getTodosMonstruos(): List<Monstruo>
    suspend fun getTodasFamilias(): List<Familia>
    suspend fun getTodosJuegos(): List<Juego>

    // Devuelve una familia buscada por nombre.
    suspend fun getFamiliaNombre(nombre: String): Familia?
    // Devuelve un juego buscado por abreviatura.
    suspend fun getJuegoNombre(abr: String): Juego?

    // Filtra por familia
    suspend fun filtroFamilia(nombre: String): List<Monstruo>

    // Filtra por juego
    suspend fun filtroJuego(abr: String): List<Monstruo>
}