package com.adyen.android.assignment.ui.screens.details

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adyen.android.assignment.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun APODDetailScreen(
    viewModel: APODDetailViewModel,
    onBackPressed: () -> Unit,
) {
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
                        onClick = {},
                        content = {
                            Icon(
                                painter = painterResource(R.drawable.ic_favorite_border),
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_warped),
                        contentDescription = stringResource(R.string.icon_description),
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = stringResource(R.string.warped),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_stars),
                        contentDescription = stringResource(R.string.icon_description),
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = stringResource(R.string.stars),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_dust),
                        contentDescription = stringResource(R.string.icon_description),
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = stringResource(R.string.gassy),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_blackhole),
                        contentDescription = stringResource(R.string.icon_description),
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = stringResource(R.string.black_hole),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}