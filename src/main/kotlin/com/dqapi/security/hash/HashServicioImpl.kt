package com.dqapi.security.hash

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class HashServicioImpl: HashService {
    /**
     * Generamos un hash con más seguridad añadiéndole una sal o
     */
    override fun generarHashConSal(valor: String, sal: Int): HashConSal {
        // Genera una String aleatoria de 32 caracteres.
        val salGen = SecureRandom.getInstance("SHA1PRNG").generateSeed(sal)
        val salHexadecimal = Hex.encodeHexString(salGen)
        // Concatenamos la sal y el valor, para que sea más seguro.
        val hash = DigestUtils.sha256Hex("$salHexadecimal$valor")

        return HashConSal(
            hash = hash,
            sal = salHexadecimal
        )
    }

    /**
     * La verificación del sistema de sal + valor que acabamos de implementar
     * en el método superior.
     */
    override fun verificar(valor: String, hashConSal: HashConSal): Boolean {
        return DigestUtils.sha256Hex(hashConSal.sal + valor) == hashConSal.hash
    }
}