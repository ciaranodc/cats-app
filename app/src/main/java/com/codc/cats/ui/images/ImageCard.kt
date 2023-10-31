package com.codc.cats.ui.images

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.codc.cats.R
import com.codc.cats.data.common.applyBlur
import com.codc.cats.data.common.applyGrayScale
import com.codc.cats.data.model.Image
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@ExperimentalGlideComposeApi
@Composable
fun ImageCard(image: Image) {
    val orientation = LocalConfiguration.current.orientation
    val haptics = LocalHapticFeedback.current

    var contextMenuPhotoId by rememberSaveable { mutableStateOf<String?>(null) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val openContextMenu = stringResource(R.string.open_context_menu)
    var imageModifier by remember {
        mutableStateOf(Modifier
            .fillMaxSize()
            .combinedClickable(onClick = { }, onLongClick = {
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                contextMenuPhotoId = image.id
            }, onLongClickLabel = openContextMenu
            )
        )
    }

    Card(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) 0.75f else 1f
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                modifier = imageModifier,
                model = image.url,
                contentDescription = stringResource(id = R.string.cat_image),
                loading = placeholder(R.drawable.empty_image),
                contentScale = ContentScale.FillWidth
            )
        }
    }

    if (contextMenuPhotoId != null) {
        ModalBottomSheet(
            onDismissRequest = { contextMenuPhotoId = null },
            sheetState = sheetState,
        ) {
            // Sheet content
            OptionsList(imageUrl = image.url, hideBottomSheet = {
                coroutineScope.launch {
                    sheetState.hide()
                    contextMenuPhotoId = null
                }
            }, applyImageFilter = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    imageModifier = when (it) {
                        ImageFilter.BLUR -> {
                            imageModifier.applyBlur()
                        }

                        ImageFilter.GRAYSCALE -> {
                            imageModifier.applyGrayScale()
                        }
                    }
                } else {
                    //todo: use work manager to apply image filter
                    // Use a ViewModel to kick this off. The ViewModel should
                    //  hand the work off to a work manager which should blur the image. Once
                    //  the blurred image is available in the ViewModel, a pop-up view should
                    //  appear showing the image.
                }
            })
        }
    }
}