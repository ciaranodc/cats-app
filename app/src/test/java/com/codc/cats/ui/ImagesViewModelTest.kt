package com.codc.cats.ui

import androidx.paging.testing.asSnapshot
import com.codc.cats.CoroutinesTestRule
import com.codc.cats.data.source.FakeImageRepository
import com.codc.cats.ui.images.ImagesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImagesViewModelTest {
    private var repository = FakeImageRepository()
    private var viewModel = ImagesViewModel(repository)

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    @Ignore("IllegalStateException: Module with the Main dispatcher had failed to initialize.")
    fun whenImagesAreLoadedInViewModel_thenCorrectNumberOfImagesIsPresent() {
        runTest {
            val images = viewModel.images
            val imagesSnapshot = images.asSnapshot()
            assertEquals(10, imagesSnapshot.size)
        }
    }


    @Test
    @Ignore("IllegalStateException: Module with the Main dispatcher had failed to initialize.")
    fun whenImagesAreLoadedInViewModel_thenDetailsOfFirstImageAreCorrect() =
        runTest {
            val firstImage = viewModel.images.asSnapshot()[0]
            assertEquals("https://cdn2.thecatapi.com/images/2fq.gif", firstImage.url)
        }
}