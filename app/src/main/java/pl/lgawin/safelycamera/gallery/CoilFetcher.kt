
package pl.lgawin.safelycamera.gallery

import android.webkit.MimeTypeMap
import coil.bitmap.BitmapPool
import coil.decode.DataSource
import coil.decode.Options
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.size.Size
import okio.buffer
import pl.lgawin.safelycamera.storage.PhotosStorage
import java.io.File

/**
 * Based on Coil's [coil.fetch.FileFetcher], but uses `secureSource()` configured for decryption
 */
fun decryptingFetcher(storage: PhotosStorage) = object : Fetcher<File> {

    override fun key(data: File) = data.path

    override suspend fun fetch(pool: BitmapPool, data: File, size: Size, options: Options): FetchResult {
        return SourceResult(
            source = storage.secureSource(data).buffer(),
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(data.extension),
            dataSource = DataSource.DISK
        )
    }
}
