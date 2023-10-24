package com.codc.cats.ui.images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.codc.cats.R
import com.codc.cats.data.common.IMAGE_MANIPULATION_WORK_NAME
import com.codc.cats.data.utils.shareImage
import com.codc.cats.data.workers.CleanupWorker

@Composable
fun OptionsList(imageUrl: String, hideBottomSheet: () -> Unit) {
    val context = LocalContext.current

    LazyColumn {

        // Share image
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 10.dp)
                    .clickable {
                        shareImage(context, imageUrl)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.Share,
                    modifier = Modifier.padding(10.dp),
                    contentDescription = stringResource(id = R.string.share_icon)
                )
                Text(text = stringResource(id = R.string.share))
            }
        }

        // Blur image (using Jetpack WorkManager)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 10.dp)
                    .clickable {
                        // TODO: Blur image. Use a ViewModel to kick this off. The ViewModel should
                        //  hand the work off to a work manager which should blur the image. Once
                        //  the blurred image is available in the ViewModel, a pop-up view should
                        //  appear showing the image.
                        val workManager = WorkManager.getInstance(context)
                        val continuation = workManager
                            .beginUniqueWork(
                                IMAGE_MANIPULATION_WORK_NAME,
                                ExistingWorkPolicy.REPLACE,
                                OneTimeWorkRequest.from(CleanupWorker::class.java)
                            )
                        continuation.enqueue()

                        hideBottomSheet()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(44.dp)
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_blur),
                    contentDescription = stringResource(id = R.string.blur_icon)
                )
                Text(text = stringResource(id = R.string.blur))
            }
        }
    }
}