package pl.lgawin.safelycamera.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("error")
fun setError(view: TextInputLayout, resId: Int?) {
    view.error = resId?.let { view.context.getString(it) }
}
