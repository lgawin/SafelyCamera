package pl.lgawin.safelycamera.login

import pl.lgawin.safelycamera.utils.toHexString
import java.security.MessageDigest

/**
 * Allows only single hardcoded password
 */
class StubbedAuthenticator : Authenticator {

    private val validPassword = "F34E609970FA42EE0CAA2BCF274E1E29770B60801426F0BE9F0605216F9C572E"

    override fun checkPassword(password: String): String? =
        with(MessageDigest.getInstance("SHA-256")) {
            digest(password.toByteArray())
                .takeIf { validPassword == digest(it).toHexString() }
                ?.toHexString()
        }
}
