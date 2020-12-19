package pl.lgawin.safelycamera.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import pl.lgawin.safelycamera.databinding.FragmentGalleryBinding
import pl.lgawin.safelycamera.toast
import java.io.File

class GalleryFragment : Fragment() {

    private val path =
        "/sdcard/Android/data/pl.lgawin.safelycamera/files/Pictures/J20201217_201315_2315534010738133014.jpg"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentGalleryBinding.inflate(inflater, container, false)
            .apply {
                image.load(File(path))
                floatingActionButton.setOnClickListener { toast("Fab clicked") }
            }
            .root
}
