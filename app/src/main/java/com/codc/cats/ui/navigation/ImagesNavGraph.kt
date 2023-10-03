package com.codc.cats.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codc.cats.ui.images.ImagesScreen
import com.codc.cats.ui.images.ImagesViewModel
import com.codc.cats.ui.navigation.AppDestinations.IMAGES_ROUTE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesNavGraph(
    navController: NavHostController,
    imagesViewModel: ImagesViewModel
) {
    NavHost(navController = navController, startDestination = IMAGES_ROUTE) {
        composable(IMAGES_ROUTE) {
            ImagesScreen(imagesViewModel)
        }
    }
}