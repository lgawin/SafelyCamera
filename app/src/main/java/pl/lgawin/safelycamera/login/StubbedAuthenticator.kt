package pl.lgawin.safelycamera.login

import pl.lgawin.safelycamera.utils.toHexString
import java.security.MessageDigest

/**
 * Allows only single hardcoded password
 */
class StubbedAuthenticator : Authenticator {

    private val validPassword = "832DBE6768E5AE676F8AAC147AD45F6658EDD94E9746EEA80DFAE99B962D553D"

    override fun checkPassword(password: String): String? =
        with(MessageDigest.getInstance("SHA-256")) {
            digest(password.toByteArray())
                .takeIf { validPassword == digest(it).toHexString() }
                ?.toHexString()
        }
}
