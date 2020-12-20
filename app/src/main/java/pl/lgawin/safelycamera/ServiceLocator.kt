package pl.lgawin.safelycamera

import android.content.Context
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.login.StubbedAuthenticator
import pl.lgawin.safelycamera.security.Ciphers
import pl.lgawin.safelycamera.security.PasswordBasedKeyProvider
import pl.lgawin.safelycamera.storage.PhotosStorage

internal class ServiceLocator private constructor(context: Context) {

    lateinit var token: String
    private val ciphers by lazy { Ciphers(PasswordBasedKeyProvider(token)) }
    val photosStorage by lazy { PhotosStorage(context, ciphers) }
    val photosRepository: PhotosRepository get() = photosStorage
    val authenticator = StubbedAuthenticator()

    companion object {
        private var serviceLocator: ServiceLocator? = null

        @Synchronized
        private fun newServiceLocator(context: Context): ServiceLocator {
            serviceLocator?.let { return it }
            val newServiceLocator = ServiceLocator(context)
            serviceLocator = newServiceLocator
            return newServiceLocator
        }

        fun instance(context: Context): ServiceLocator = serviceLocator ?: newServiceLocator(context)
    }
}

internal val Context.serviceLocator: ServiceLocator
    get() = ServiceLocator.instance(this)
