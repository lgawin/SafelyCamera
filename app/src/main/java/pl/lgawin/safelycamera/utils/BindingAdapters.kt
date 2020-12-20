package pl.lgawin.safelycamera.utils

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("bitmap")
fun setBitmap(view: ImageView, bitmap: Bitmap?) {
    view.setImageBitmap(bitmap)
}

@BindingAdapter("error")
fun setBitmap(view: TextInputLayout, resId: Int?) {
    view.error = resId?.let { view.context.getString(it) }
}
