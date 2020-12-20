package pl.lgawin.safelycamera.testing

import androidx.test.platform.app.InstrumentationRegistry

interface BaseTest {
    val context
        get() = InstrumentationRegistry.getInstrumentation().targetContext!!
}
