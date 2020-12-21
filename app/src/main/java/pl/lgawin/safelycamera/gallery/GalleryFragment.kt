package pl.lgawin.safelycamera.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.camera.CameraDispatcher
import pl.lgawin.safelycamera.camera.ExternalActivityCameraDispatcher.Companion.externalIntentCameraDispatcher
import pl.lgawin.safelycamera.databinding.FragmentGalleryBinding
import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.login.LoginRequiredException
import pl.lgawin.safelycamera.storage.PhotosStorage
import pl.lgawin.safelycamera.utils.State
import pl.lgawin.safelycamera.utils.State.*
import pl.lgawin.safelycamera.utils.simpleFactory
import pl.lgawin.safelycamera.utils.toast

class GalleryFragment(
    private val photosRepository: PhotosRepository,
    private val photosStorage: PhotosStorage,
    private val photosAdapter: ListAdapter<Photo, *>
) : Fragment(), GalleryHandler {

    private val viewModel by viewModels<GalleryViewModel> {
        simpleFactory { GalleryViewModel(photosRepository) }
    }

    private val cameraDispatcher: CameraDispatcher by lazy {
        externalIntentCameraDispatcher(
            this,
            photosStorage,
            REQUEST_IMAGE_CAPTURE,
            onResult = { viewModel.refresh() },
        ) { toast("RESULT_CANCELED") }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentGalleryBinding.inflate(inflater, container, false)
            .apply {
                adapter = photosAdapter
                vm = viewModel
                handler = this@GalleryFragment
                lifecycleOwner = viewLifecycleOwner
            }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.photos.observe(viewLifecycleOwner, {
            when (it) {
                Loading -> Unit
                is Success<List<Photo>> -> photosAdapter.submitList(it.data)
                is Failure -> handleFailure(it.error)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> cameraDispatcher.dispatchResult(resultCode, data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onTakePicture() {
        cameraDispatcher.dispatchTakePicture()
    }

    private fun handleFailure(error: Throwable) {
        if (error is LoginRequiredException) findNavController().navigate(R.id.action_galleryFragment_to_loginFragment)
        else showErrorDialog(error)
    }

    private fun showErrorDialog(error: Throwable) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.fatal_error_title))
            .setMessage(getString(R.string.fatal_error_message, error.message))
            .setPositiveButton(R.string.fatal_error_action) { _, _ -> requireActivity().finish() }
            .show()
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 3452
    }
}

interface GalleryHandler {
    fun onTakePicture()
}
