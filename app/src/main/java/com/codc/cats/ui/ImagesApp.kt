package com.codc.cats.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.codc.cats.ui.navigation.ImagesNavGraph
import com.codc.cats.ui.images.ImagesViewModel

@Composable
fun ImagesApp(imagesViewModel: ImagesViewModel) {
    val navController = rememberNavController()
    ImagesNavGraph(navController, imagesViewModel)
}
