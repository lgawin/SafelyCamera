package pl.lgawin.safelycamera.dev

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DevActivityViewModel : ViewModel() {

    private var _thumbnail = MutableLiveData<Bitmap>()
    val thumbnail = _thumbnail as LiveData<Bitmap>
    var bitmap: Bitmap?
        get() = _thumbnail.value
        set(value) {
            _thumbnail.value = value
        }
}
