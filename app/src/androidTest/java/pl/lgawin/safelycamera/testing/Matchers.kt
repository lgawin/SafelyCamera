package pl.lgawin.safelycamera.testing

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

// checks if `TextInputLayout` has error
fun hasError() = object : BaseMatcher<View>() {

    override fun matches(item: Any?): Boolean {
        if (item !is TextInputLayout) return false
        return item.error?.isNotEmpty() == true
    }

    override fun describeTo(description: Description?) {
        description?.appendText("has error")
    }
}
