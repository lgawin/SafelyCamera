package pl.lgawin.safelycamera

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import pl.lgawin.safelycamera.domain.Photo

@Category(CheckModuleTest::class)
class CheckModulesTest : KoinTest {

    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @get:Rule
    val rule: TestRule = RuleChain.outerRule(InstantTaskExecutorRule())
        .around(mockProvider)

    @Test
    fun checkAllModules() = checkModules {
        androidContext(mockk(relaxed = true))
        modules(galleryModule, securityModule, loginModule, fakeCoilModule)
    }
}

val fakeCoilModule = module {
    single<ImageLoader> { mockk() }
    single<ListAdapter<Photo, *>> { mockk() }
}
