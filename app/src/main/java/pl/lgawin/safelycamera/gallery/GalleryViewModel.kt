package pl.lgawin.safelycamera.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository

class GalleryViewModel(private val photosRepository: PhotosRepository) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> get() = _photos

    init {
        loadPhotos()
    }

    private fun loadPhotos() = viewModelScope.launch {
        _photos.value = photosRepository.getPhotos()
    }

    fun refresh() = loadPhotos()
}
