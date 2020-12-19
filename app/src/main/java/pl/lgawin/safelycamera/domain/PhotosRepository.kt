package pl.lgawin.safelycamera.domain

typealias Photo = String

interface PhotosRepository {
    suspend fun getPhotos(): List<Photo>
}
