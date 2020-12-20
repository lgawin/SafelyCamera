package pl.lgawin.safelycamera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.gallery.GalleryFragment
import pl.lgawin.safelycamera.login.Authenticator
import pl.lgawin.safelycamera.login.LoginFragment
import pl.lgawin.safelycamera.storage.PhotosStorage

class MainActivity : AppCompatActivity() {

    private val photosRepository: PhotosRepository by lazy { PhotosStorage(this) }
    // FIXME provide valid implementation
    private val authenticator = object : Authenticator {
        override fun checkPassword(password: String) = "p@ssword" == password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return when (className) {
                    GalleryFragment::class.java.name -> GalleryFragment(photosRepository)
                    LoginFragment::class.java.name -> LoginFragment(authenticator)
                    else -> super.instantiate(classLoader, className)
                }
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
