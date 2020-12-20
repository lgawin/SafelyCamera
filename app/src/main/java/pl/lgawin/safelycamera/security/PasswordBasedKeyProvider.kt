package pl.lgawin.safelycamera.security

import java.security.MessageDigest
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class PasswordBasedKeyProvider(private val tokenHolder: TokenHolder) : KeyProvider {

    private val token: String
        get() = tokenHolder.token.orEmpty()

    override val key: SecretKey by lazy {
        with(token) {
            val salt = MessageDigest.getInstance("SHA-512").digest(toByteArray())
            val keySpec: KeySpec = PBEKeySpec(toCharArray(), salt, 10000, 256)
            SecretKeyFactory.getInstance(CryptoConfiguration.algorithm).generateSecret(keySpec)
        }
    }
}
