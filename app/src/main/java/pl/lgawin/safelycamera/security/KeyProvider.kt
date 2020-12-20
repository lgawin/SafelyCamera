package pl.lgawin.safelycamera.security

import java.security.Key

interface KeyProvider {
    val key: Key
}
