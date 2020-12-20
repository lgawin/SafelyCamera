package pl.lgawin.safelycamera.testing

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider

interface FragmentTest {
    val testNavHostController get() = TestNavHostController(ApplicationProvider.getApplicationContext())
    fun testNavController(graph: Int) = testNavHostController.apply { setGraph(graph) }
}
