package pl.lgawin.safelycamera

import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import coil.util.DebugLogger
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module
import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.gallery.GalleryAdapter
import pl.lgawin.safelycamera.gallery.GalleryFragment
import pl.lgawin.safelycamera.gallery.decryptingFetcher
import pl.lgawin.safelycamera.login.Authenticator
import pl.lgawin.safelycamera.login.LoginFragment
import pl.lgawin.safelycamera.login.StubbedAuthenticator
import pl.lgawin.safelycamera.security.Ciphers
import pl.lgawin.safelycamera.security.CryptoConfiguration
import pl.lgawin.safelycamera.security.KeyProvider
import pl.lgawin.safelycamera.security.PasswordBasedKeyProvider
import pl.lgawin.safelycamera.security.TokenHolder
import pl.lgawin.safelycamera.storage.PhotosStorage

val securityModule = module {
    factory { Ciphers(get()) }
    factory<KeyProvider> { PasswordBasedKeyProvider(get()) }
    single<TokenHolder> { CryptoConfiguration }
}

val loginModule = module {
    fragment { LoginFragment(get(), get()) }
    factory<Authenticator> { StubbedAuthenticator() }
}

val galleryModule  = module {
    fragment { GalleryFragment(get(), get(), get()) }
    single<PhotosRepository> { get<PhotosStorage>() }
    single { PhotosStorage(androidContext(), get()) }
}

val coilModule = module {
    factory<ListAdapter<Photo, *>> { GalleryAdapter(get()) }
    single {
        ImageLoader.Builder(androidContext()).run {
            if (BuildConfig.DEBUG) logger(DebugLogger())
            componentRegistry { add(decryptingFetcher(get())) }
            build()
        }
    }
}
