package com.example.segurosfacil.data.repository

import org.mindrot.jbcrypt.BCrypt

fun encriptarPassword(passwordPlano: String): String {
    return BCrypt.hashpw(passwordPlano, BCrypt.gensalt())
}

fun verificarPassword(passwordPlano: String, passwordHash: String): Boolean {
    return BCrypt.checkpw(passwordPlano, passwordHash)
}