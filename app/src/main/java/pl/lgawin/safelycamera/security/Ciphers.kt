package pl.lgawin.safelycamera.security

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class Ciphers(private val keyProvider: KeyProvider) {

    fun encryptMode(): Cipher = Cipher.getInstance(CryptoConfiguration.algorithm).apply {
        init(Cipher.ENCRYPT_MODE, keyProvider.key)
    }

    fun decryptMode(initVector: ByteArray): Cipher = Cipher.getInstance(CryptoConfiguration.algorithm).apply {
        init(Cipher.DECRYPT_MODE, keyProvider.key, IvParameterSpec(initVector))
    }
}
