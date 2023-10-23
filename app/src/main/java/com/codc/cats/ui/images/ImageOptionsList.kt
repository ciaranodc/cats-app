package com.codc.cats.ui.images

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codc.cats.R

@Composable
fun ImageOptionsList() {
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 10.dp),
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
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 10.dp),
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