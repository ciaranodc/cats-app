package com.codc.cats.ui

import androidx.paging.testing.asSnapshot
import com.codc.cats.CoroutinesTestRule
import com.codc.cats.data.source.FakeImageRepository
import com.codc.cats.data.source.repository.ImageRepository
import com.codc.cats.ui.images.ImagesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ImagesViewModelTest {
    private lateinit var repository: ImageRepository
    private lateinit var viewModel: ImagesViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        repository = FakeImageRepository()
        viewModel = ImagesViewModel(repository)
    }

    @Test
    fun whenImagesAreLoadedInViewModel_thenCorrectNumberOfImagesIsPresent() {
        runTest {
            val images = viewModel.images
            val imagesSnapshot = images.asSnapshot()
            assertEquals(10, imagesSnapshot.size)
        }
    }


    @Test
    fun whenImagesAreLoadedInViewModel_thenDetailsOfFirstImageAreCorrect() =
        runTest {
            val firstImage = viewModel.images.asSnapshot()[0]
            assertEquals("https://cdn2.thecatapi.com/images/2fq.gif", firstImage.url)
        }
}