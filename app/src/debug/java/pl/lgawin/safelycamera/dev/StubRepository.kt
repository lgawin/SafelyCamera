package pl.lgawin.safelycamera.dev

import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository
import java.io.File

fun stubRepository(storage: File): PhotosRepository =
    object : PhotosRepository {
        override suspend fun getPhotos(): List<Photo> =
            storage.listFiles()?.toList().orEmpty()
                .map { it.absolutePath }
    }
