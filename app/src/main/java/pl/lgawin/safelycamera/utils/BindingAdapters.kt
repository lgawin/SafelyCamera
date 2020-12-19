package pl.lgawin.safelycamera.utils

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("bitmap")
fun setBitmap(view: ImageView, bitmap: Bitmap?) {
    view.setImageBitmap(bitmap)
}
