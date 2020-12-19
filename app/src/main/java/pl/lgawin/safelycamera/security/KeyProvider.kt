package pl.lgawin.safelycamera.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object KeyProvider {

    private const val keyAlias = "safely-camera-key"

    val secretKey: SecretKey
        get() {
            val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
            if (!keyStore.aliases().asSequence().contains(keyAlias)) return generateKey(keyAlias)

            val entry = keyStore.getEntry(keyAlias, null)
            require(entry is KeyStore.SecretKeyEntry)
            return entry.secretKey
        }

    private fun generateKey(alias: String): SecretKey {
        val keySpec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .run {
                setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                build()
            }
        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore").run {
            init(keySpec)
            generateKey()
        }
    }
}
