package pl.lgawin.safelycamera.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import pl.lgawin.safelycamera.databinding.FragmentGalleryBinding
import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.toast

class GalleryFragment constructor(private val photosRepository: PhotosRepository) : Fragment() {

    private val viewModel by viewModels<GalleryViewModel> {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = GalleryViewModel(photosRepository) as T
        }
    }

    private val photosAdapter: ListAdapter<Photo, PhotoViewHolder> = GalleryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentGalleryBinding.inflate(inflater, container, false)
            .apply {
                floatingActionButton.setOnClickListener { toast("Fab clicked") }
                adapter = photosAdapter
                vm = viewModel
                lifecycleOwner = this@GalleryFragment
            }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.photos.observe(viewLifecycleOwner, Observer(photosAdapter::submitList))
    }
}
