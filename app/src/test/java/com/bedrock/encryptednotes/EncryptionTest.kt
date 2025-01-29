package com.bedrock.encryptednotes

import android.content.Context
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
    @Test
    fun testLotsOfSpaces() {
        val encryption = MockedEncryption("somekey")
        val endingPattern = "ld!"
        val textToEncrypt = "if we is to is to is to is to is to is to is to is to is to is g is to is to is to is to is to it to it to is h is to is to is to is to is i is to it to it to it to is to is to is y or u is to is to it to is to is to is to is to is to the"

        val encryptedText = encryption.encrypt(textToEncrypt)
        assert(textToEncrypt != encryptedText)
        assert(!encryptedText.contains(endingPattern))
        val decryptedText = encryption.decrypt(encryptedText)

        assertEquals(textToEncrypt, decryptedText)
    }
}

class MockedEncryption(key: String) : Encryption(key) {

}
