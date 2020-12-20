package pl.lgawin.safelycamera

import android.content.Context
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.login.Authenticator
import pl.lgawin.safelycamera.storage.PhotosStorage

internal class ServiceLocator(context: Context) {

    val photosRepository: PhotosRepository = PhotosStorage(context)

    // FIXME provide valid implementation
    val authenticator = object : Authenticator {
        override fun checkPassword(password: String) = "p@ssword" == password
    }
}
