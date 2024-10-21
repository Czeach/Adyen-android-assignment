package com.adyen.android.assignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture
import com.adyen.android.assignment.ui.components.AdditionalInfoRow
import com.adyen.android.assignment.ui.viewmodel.APODViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun APODDetailScreen(
    viewModel: APODViewModel,
    onBackPressed: () -> Unit,
) {

    val favouriteState by viewModel.favouriteState.collectAsStateWithLifecycle()
    viewModel.getAPODByTitle(viewModel.apodTitle.toString())

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_bar_title),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackPressed()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = stringResource(R.string.icon_description)
                            )
                        }
                    )
                }
            )
        },
    ) { padding ->
        AsyncImage(
            model = viewModel.apodUrl,
            contentDescription = stringResource(R.string.image_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                        startY = 0f,
                        endY = 1100f
                    )
                )
        )
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 18.dp)
        ) {
            handleFavouriteState(viewModel, favouriteState)
            Column {
                Text(
                    text = viewModel.apodTitle.toString(),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(top = 32.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = viewModel.apodDate.toString(),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    IconButton(
                        onClick = {
                            viewModel.onFavouriteStateChanged(!favouriteState)
                        },
                        content = {
                            Icon(
                                painter = if (favouriteState) painterResource(
                                    R.drawable.ic_favorite_filled
                                )
                                else painterResource(
                                    R.drawable.ic_favorite_border
                                ),
                                contentDescription = stringResource(R.string.icon_description),
                                tint = MaterialTheme.colorScheme.secondary,
                            )
                        }
                    )
                }
                Text(
                    text = viewModel.apodExplanation.toString(),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .wrapContentHeight()
                )
                AdditionalInfoRow(R.drawable.ic_warped, stringResource(R.string.warped))
                AdditionalInfoRow(R.drawable.ic_stars, stringResource(R.string.stars))
                AdditionalInfoRow(R.drawable.ic_dust, stringResource(R.string.gassy))
                AdditionalInfoRow(R.drawable.ic_blackhole, stringResource(R.string.black_hole))
            }
        }
    }
}

private fun handleFavouriteState(viewModel: APODViewModel, favouriteState: Boolean) {
    when (favouriteState) {
        true -> {
            viewModel.insertAPOD(
                LocalAstronomyPicture(
                    viewModel.apodTitle.toString(),
                    viewModel.apodDate.toString(),
                    viewModel.apodExplanation.toString(),
                    viewModel.apodUrl.toString(),
                    true
                )
            )
        }

        else -> {
            viewModel.deleteAPOD(viewModel.apodTitle.toString())
        }
    }
}