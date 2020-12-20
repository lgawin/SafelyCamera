package pl.lgawin.safelycamera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import pl.lgawin.safelycamera.gallery.GalleryFragment
import pl.lgawin.safelycamera.login.LoginFragment

class MainActivity : AppCompatActivity() {

    private val customFragmentFactory
        get() = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return when (className) {
                    GalleryFragment::class.java.name -> GalleryFragment(serviceLocator.photosRepository)
                    LoginFragment::class.java.name -> LoginFragment(serviceLocator.authenticator)
                    else -> super.instantiate(classLoader, className)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = customFragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
