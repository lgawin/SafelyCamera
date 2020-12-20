package pl.lgawin.safelycamera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val serviceLocator by lazy { ServiceLocator(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = serviceLocator.customFragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

