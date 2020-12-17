package pl.lgawin.safelycamera

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private var _thumbnail = MutableLiveData<Bitmap>()
    val thumbnail = _thumbnail as LiveData<Bitmap>
    var bitmap: Bitmap?
        get() = _thumbnail.value
        set(value) {
            _thumbnail.value = value
        }
}

@BindingAdapter("bitmap")
fun setBitmap(view: ImageView, bitmap: Bitmap?) {
    view.setImageBitmap(bitmap)
}
