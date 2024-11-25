package com.bedrock.encryptednotes

import com.bedrock.encryptednotes.misc.Encryption
import org.junit.Assert.assertEquals
import org.junit.Test


class EncryptionTest {

    @Test
    fun testEncryptDecryptWithLongKey() {
        val encryption = MockedEncryption("keythatislongerthanthetextButloremipsumdolorsitamet")
        val endingPattern = "ld!"
        val textToEncrypt = "Hello, World!"

        val encryptedText = encryption.encrypt(textToEncrypt)
        assert(textToEncrypt != encryptedText)
        assert(!encryptedText.contains(endingPattern))
        val decryptedText = encryption.decrypt(encryptedText)

        assertEquals(textToEncrypt, decryptedText)
    }
    @Test
    fun testEncryptDecryptWithShorterKey() {
        val encryption = MockedEncryption("key")
        val endingPattern = "ld!"
        val textToEncrypt = "Hello, World!"

        val encryptedText = encryption.encrypt(textToEncrypt)
        assert(textToEncrypt != encryptedText)
        assert(!encryptedText.contains(endingPattern))
        val decryptedText = encryption.decrypt(encryptedText)

        assertEquals(textToEncrypt, decryptedText)
    }
}

class MockedEncryption(val mockKey: String) : Encryption() {
    override fun getKeyFromKeystore(): String {
        return mockKey
    }
}