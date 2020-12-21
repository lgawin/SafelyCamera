package pl.lgawin.safelycamera.gallery

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
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.domain.Photo
import java.io.File

class GalleryAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<String, PhotoViewHolder>(PhotoItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PhotoViewHolder(imageLoader, parent)

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
