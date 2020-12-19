package pl.lgawin.safelycamera.storage

import android.content.Context
import android.os.Environment
import okio.Sink
import okio.Source
import okio.buffer
import okio.cipherSink
import okio.cipherSource
import okio.sink
import okio.source
import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.domain.PhotosRepository
import pl.lgawin.safelycamera.security.Ciphers
import pl.lgawin.safelycamera.utils.byteArrayFromHexString
import pl.lgawin.safelycamera.utils.toHexString
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PhotosStorage(private val directory: File, private val tempDir: File) : PhotosRepository {

    constructor(context: Context) : this(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!,
        context.externalCacheDir ?: context.cacheDir
    )

    fun createTempFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.ROOT).format(Date())
        return File.createTempFile("J${timeStamp}_", ".jpg", tempDir)
    }

    fun moveToSecure(file: File) {
        file.source().buffer().use { source ->
            secureSink(file).buffer().use { sink ->
                sink.writeAll(source)
                sink.close()
            }
            source.close()
        }
        file.delete()
    }

    fun secureSink(file: File): Sink {
        val cipher = Ciphers.encryptMode()
        val fileName = file.nameWithoutExtension + "." + cipher.iv.toHexString()
        return File(directory, fileName).sink().cipherSink(cipher)
    }

    fun secureSource(data: File): Source {
        val iv = data.extension.byteArrayFromHexString()
        return data.source().cipherSource(Ciphers.decryptMode(iv))
    }

    override suspend fun getPhotos(): List<Photo> = directory.listFiles()?.toList().orEmpty().map { it.absolutePath }
}
