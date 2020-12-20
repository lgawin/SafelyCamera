package pl.lgawin.safelycamera.gallery

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.util.DebugLogger
import pl.lgawin.safelycamera.BuildConfig
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.domain.Photo
import pl.lgawin.safelycamera.serviceLocator
import java.io.File

class GalleryAdapter(context: Context) : ListAdapter<String, PhotoViewHolder>(PhotoItemDiffCallback()) {

    private val loader = ImageLoader.Builder(context).run {
        if (BuildConfig.DEBUG) logger(DebugLogger())
        componentRegistry { add(decryptingFetcher(context.serviceLocator.photosStorage)) }
        build()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PhotoViewHolder(loader, parent)

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) = holder.bind(getItem(position))
}

class PhotoViewHolder private constructor(
    private val loader: ImageLoader,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val imageView get() = itemView as ImageView

    fun bind(item: Photo) {
        imageView.load(File(item), loader) {
            placeholder(R.drawable.ic_image)
            error(R.drawable.ic_image_broken)
        }
    }

    companion object {
        operator fun invoke(loader: ImageLoader, parent: ViewGroup): PhotoViewHolder =
            PhotoViewHolder(loader, ImageView(parent.context).apply {
                layoutParams = LayoutParams(MATCH_PARENT, 720)
            })
    }
}

class PhotoItemDiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = areContentsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}
