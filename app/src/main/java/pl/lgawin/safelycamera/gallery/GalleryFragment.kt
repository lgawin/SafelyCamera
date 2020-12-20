package pl.lgawin.safelycamera.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ListAdapter
import pl.lgawin.safelycamera.camera.ExternalActivityCameraDispatcher.Companion.externalIntentCameraDispatcher
import pl.lgawin.safelycamera.databinding.FragmentGalleryBinding
import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.utils.simpleFactory
import pl.lgawin.safelycamera.utils.toast

class GalleryFragment constructor(private val photosRepository: PhotosRepository) : Fragment() {

    private val viewModel by viewModels<GalleryViewModel> {
        simpleFactory { GalleryViewModel(photosRepository) }
    }

    private val photosAdapter: ListAdapter<Photo, PhotoViewHolder> by lazy { GalleryAdapter(requireContext()) }

    private val cameraDispatcher by lazy {
        externalIntentCameraDispatcher(
            this,
            REQUEST_IMAGE_CAPTURE,
            onResult = { viewModel.refresh() },
        ) { toast("RESULT_CANCELED") }
    }

    private val args by navArgs<GalleryFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentGalleryBinding.inflate(inflater, container, false)
            .apply {
                floatingActionButton.setOnClickListener { cameraDispatcher.dispatchTakePicture() }
                adapter = photosAdapter
                vm = viewModel
                lifecycleOwner = this@GalleryFragment
            }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.photos.observe(viewLifecycleOwner, Observer(photosAdapter::submitList))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> cameraDispatcher.dispatchResult(resultCode, data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 3452
    }
}
