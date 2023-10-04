package com.codc.cats.ui.images

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.codc.cats.R
import com.codc.cats.data.model.Image
import com.codc.cats.ui.composables.CentralProgressIndicator
import com.codc.cats.ui.composables.EmptyView
import com.codc.cats.ui.composables.ErrorRetryButton

@ExperimentalMaterial3Api
@Composable
fun ImagesScreen(imagesViewModel: ImagesViewModel) {
    val images = imagesViewModel.retrieveImages().collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.app_bar_title)) },
            actions = {
                IconButton(onClick = { images.refresh() }) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh cat images"
                    )
                }
            })
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            ImageScreenContent(images)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageScreenContent(images: LazyPagingItems<Image>) {
    LazyColumn {
        images.apply {
            when {
                loadState.refresh is LoadState.Loading -> item {
                    CentralProgressIndicator(modifier = Modifier.fillParentMaxSize())
                }

                loadState.mediator?.refresh is LoadState.Error && images.itemCount == 0 -> {
                    item {
                        ErrorRetryButton(onClick = { retry() })
                    }
                }

                loadState.refresh is LoadState.NotLoading && images.itemCount == 0 -> {
                    item {
                        EmptyView(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(10.dp)
                        )
                    }
                }

                loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading -> {
                    items(count = images.itemCount) { index ->
                        images[index]?.let {
                            ImageCard(image = it)
                        }
                    }
                }

            }
        }
    }
}