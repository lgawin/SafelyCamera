package pl.lgawin.safelycamera.security

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

object Ciphers {

    private const val algorithm = "AES/CBC/PKCS7Padding"

    fun encryptMode(): Cipher = Cipher.getInstance(algorithm).apply {
        init(Cipher.ENCRYPT_MODE, KeyProvider.secretKey)
    }

    fun decryptMode(initVector: ByteArray): Cipher = Cipher.getInstance(algorithm).apply {
        init(Cipher.DECRYPT_MODE, KeyProvider.secretKey, IvParameterSpec(initVector))
    }
}
