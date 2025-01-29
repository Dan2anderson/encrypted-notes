package com.bedrock.encryptednotes.misc

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

open class Encryption(val key: String) {

    fun encrypt(textToEncrypt: String): String {
        val key = key
        val keyLength = key.length
        val encryptedChars = textToEncrypt.mapIndexed { index, char ->
            char.code xor key[index % keyLength].code
        }
        return encryptedChars.joinToString(separator = "") { it.toChar().toString() }
    }

    fun decrypt(text: String): String {
        val key = key
        val keyLength = key.length
        val decryptedChars = text.mapIndexed { index, char ->
            char.code xor key[index % keyLength].code
        }
        return decryptedChars.joinToString(separator = "") { it.toChar().toString() }
    }
}