package pl.lgawin.safelycamera.dev

import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository
import java.io.File

class StubRepository(private val photos: List<Photo>) : PhotosRepository {
    override suspend fun getPhotos(): List<Photo> = photos
}

fun stubRepository(storage: File): StubRepository {
    val files = storage.listFiles()?.toList().orEmpty()
    return StubRepository(files.map { it.absolutePath })
}
