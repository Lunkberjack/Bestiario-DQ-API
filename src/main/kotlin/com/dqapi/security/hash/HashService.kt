package com.dqapi.security.hash

interface HashService {
    fun generarHashConSal(
        valor: String,
        // Por defecto, 32 bits de sal.
        sal: Int = 32
    ): HashConSal

    fun verificar(
        valor: String,
        hashConSal: HashConSal
    ): Boolean
}