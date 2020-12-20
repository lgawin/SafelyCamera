package pl.lgawin.safelycamera.security

import java.security.MessageDigest
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class PasswordBasedKeyProvider(private val password: String) : KeyProvider {

    override val key: SecretKey by lazy {
        val salt = MessageDigest.getInstance("SHA-512").digest(password.toByteArray())
        val keySpec: KeySpec = PBEKeySpec(password.toCharArray(), salt, 10000, 256)
        SecretKeyFactory.getInstance(CryptoConfiguration.algorithm).generateSecret(keySpec)
    }
}
