package com.codc.cats.ui.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.codc.cats.R
import com.codc.cats.data.source.local.database.entity.ImageEntity

@ExperimentalGlideComposeApi
@Composable
fun ImageCard(image: ImageEntity) {
    Card(
        modifier = Modifier.padding(all = 10.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            GlideImage(
                model = image.downloadUrl,
                contentDescription = image.author,
                loading = placeholder(R.drawable.empty_image),
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = "Author: ${image.author}",
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}