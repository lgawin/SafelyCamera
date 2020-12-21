package pl.lgawin.safelycamera

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class SafelyCameraApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SafelyCameraApp)
            fragmentFactory()
            modules(appModule)
        }
    }
}

val appModule = listOf(
    coilModule,
    loginModule,
    galleryModule,
    securityModule
)
