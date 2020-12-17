package pl.lgawin.safelycamera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.lgawin.safelycamera.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentGalleryBinding.inflate(inflater, container, false)
            .apply { floatingActionButton.setOnClickListener { toast("Fab clicked") } }
            .root
}
