package pl.lgawin.safelycamera

import android.content.Context
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.login.Authenticator
import pl.lgawin.safelycamera.storage.PhotosStorage
import pl.lgawin.safelycamera.utils.toHexString
import java.security.MessageDigest

internal class ServiceLocator(context: Context) {

    val photosRepository: PhotosRepository = PhotosStorage(context)

    val authenticator = object : Authenticator {
        override fun checkPassword(password: String): String? =
            with(MessageDigest.getInstance("SHA-256")) {
                val hash1 = digest(password.toByteArray())
                val hash2 = digest(hash1).toHexString()
                hash1
                    .takeIf { "832DBE6768E5AE676F8AAC147AD45F6658EDD94E9746EEA80DFAE99B962D553D" == hash2 }
                    ?.toHexString()
            }
    }
}
