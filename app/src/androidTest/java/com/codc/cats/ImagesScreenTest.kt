package com.codc.cats

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codc.cats.data.source.local.database.entity.ImageEntity
import com.codc.cats.data.source.repository.ImageRepository
import com.codc.cats.ui.images.ImagesScreen
import com.codc.cats.ui.images.ImagesViewModel
import com.codc.sharedtest.FakePagingSource
import kotlinx.coroutines.flow.Flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class ImagesScreenTest {
    private lateinit var repository: ImageRepository
    private lateinit var viewModel: ImagesViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        repository = object : ImageRepository {
            override fun getImages(): Flow<PagingData<ImageEntity>> {
                return Pager(PagingConfig(pageSize = 10)) { FakePagingSource(emptyList()) }.flow
            }
        }
        viewModel = ImagesViewModel(repository)
    }

    @Test
    fun imagesScreenAppBarTitleShouldSayUnlimitedCats() {
        composeTestRule.setContent {
            ImagesScreen(viewModel)
        }

        composeTestRule.onNodeWithText("Unlimited cats \uD83D\uDC08").assertIsDisplayed()
    }
}