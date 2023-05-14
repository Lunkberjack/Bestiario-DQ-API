package com.dqapi.data.usuario

/**
 * Deberán ser implementadas por la clase UsuarioDataSourceImpl.
 */
interface UsuarioDataSource {
    // Puede devolver un Usuario o un nulo.
    suspend fun getUsuarioNombre(username: String): Usuario?
    // Devuelve true si se insertó y false si no.
    suspend fun aniadirUsuario(usuario: Usuario): Boolean
}