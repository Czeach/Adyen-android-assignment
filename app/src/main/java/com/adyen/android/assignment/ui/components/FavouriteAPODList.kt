package com.adyen.android.assignment.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture

@Composable
fun FavouriteAPODList(
    apods: List<LocalAstronomyPicture>,
    onAPODClicked: (String, String, String, String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.favourite_heading),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 8.dp, bottom = 12.dp)
        )
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
        ) {
            items(
                items = apods,
                key = {
                    apods.indexOf(it)
                }
            ) { apod ->
                APODListItem(
                    modifier = Modifier
                        .padding(start = 22.dp, top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    title = apod.title,
                    imageUrl = apod.url,
                    date = apod.date,
                    onAPODClicked = {
                        onAPODClicked(
                            apod.title,
                            apod.date,
                            apod.explanation,
                            apod.url
                        )
                    }
                )
            }
        }
    }
}