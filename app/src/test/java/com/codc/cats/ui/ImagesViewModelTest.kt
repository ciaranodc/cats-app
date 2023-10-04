package com.codc.cats.ui

import androidx.paging.testing.asSnapshot
import com.codc.cats.CoroutinesTestRule
import com.codc.cats.data.source.FakeImageRepository
import com.codc.cats.ui.images.ImagesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class ImagesViewModelTest {
    private var repository = FakeImageRepository()
    private var viewModel = ImagesViewModel(repository)

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun whenImagesAreLoadedInViewModel_thenCorrectNumberOfImagesIsPresent() {
        coroutinesTestRule.testDispatcher.dispatch(EmptyCoroutineContext) {
            runTest {
                val images = viewModel.images
                val imagesSnapshot = images.asSnapshot()

                assertEquals(30, imagesSnapshot.size)
            }
        }
    }


    @Test
    fun whenImagesAreLoadedInViewModel_thenDetailsOfFirstImageAreCorrect() =
        coroutinesTestRule.testDispatcher.dispatch(EmptyCoroutineContext) {
            runTest {
                val firstImage = viewModel.images.asSnapshot()[0]

                assertEquals("0", firstImage.id)
                assertEquals("Alejandro Escamilla", firstImage.author)
                assertEquals("https://picsum.photos/id/0/5000/3333", firstImage.downloadUrl)
            }
        }
}