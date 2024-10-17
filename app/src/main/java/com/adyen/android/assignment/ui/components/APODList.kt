package com.adyen.android.assignment.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.AstronomyPicture

@Composable
fun LatestAPODList(
    apods: List<AstronomyPicture>,
    onAPODClicked: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.latest_heading),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 8.dp, bottom = 12.dp)
        )
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                items = apods,
                key = {
                    apods.indexOf(it)
                }
            ) { apod ->
                APODListItem(
                    modifier = Modifier
                        .padding(start = 22.dp, top = 5.dp, bottom = 5.dp),
                    apod = apod,
                    onAPODClicked = { onAPODClicked("")}
                )
            }
        }
    }
}

@Composable
private fun APODListItem(
    modifier: Modifier,
    apod: AstronomyPicture,
    onAPODClicked: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .size(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
            )
        ) {
            AsyncImage(
                model = apod.url,
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onAPODClicked()
                    }
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        ) {
            Text(
                text = apod.title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(
                modifier = Modifier
                    .height(6.dp)
            )
            Text(
                text = apod.date,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}