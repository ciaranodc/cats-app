package com.codc.cats.ui.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.codc.cats.R
import com.codc.cats.data.model.Image
import com.codc.cats.data.source.local.database.entity.ImageEntity

@ExperimentalGlideComposeApi
@Composable
fun ImageCard(image: Image) {
    Card(
        modifier = Modifier.padding(all = 10.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            GlideImage(
                model = image.url,
                contentDescription = stringResource(id = R.string.cat_image),
                loading = placeholder(R.drawable.empty_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}