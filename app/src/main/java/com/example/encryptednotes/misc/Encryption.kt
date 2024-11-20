package com.example.encryptednotes.misc

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

open class Encryption {


    private val keyAlias = "encryptionKeyAlias"


    open fun getKeyFromKeystore(): String {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

        return if (keyStore.containsAlias(keyAlias)) {
            val secretKeyEntry = keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry
            secretKeyEntry.secretKey.toString()
        } else {
            createKey().toString()
        }
    }

    fun createKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    fun encrypt(textToEncrypt: String): String {
        val key = getKeyFromKeystore()
        val keyLength = key.length
        val encryptedChars = textToEncrypt.mapIndexed { index, char ->
            char.code xor key[index % keyLength].code
        }
        return encryptedChars.joinToString(separator = "") { it.toChar().toString() }
    }

    fun decrypt(text: String): String {
        val key = getKeyFromKeystore()
        val keyLength = key.length
        val decryptedChars = text.mapIndexed { index, char ->
            char.code xor key[index % keyLength].code
        }
        return decryptedChars.joinToString(separator = "") { it.toChar().toString() }
    }
}