package pl.lgawin.safelycamera.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.utils.State
import pl.lgawin.safelycamera.utils.State.Success
import pl.lgawin.safelycamera.utils.loadData

class GalleryViewModel(private val photosRepository: PhotosRepository) : ViewModel() {

    private val state = MutableLiveData<State<List<Photo>>>()
    val photos: MutableLiveData<State<List<Photo>>> get() = state
    val isEmpty = photos.map {
        it is Success<List<Photo>> && it.data.isNullOrEmpty()
    }

    init {
        loadPhotos()
    }

    private fun loadPhotos() = viewModelScope.launch {
        state.loadData { photosRepository.getPhotos() }
    }

    fun refresh() = loadPhotos()
}
