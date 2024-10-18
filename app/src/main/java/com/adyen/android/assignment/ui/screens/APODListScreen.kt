package com.adyen.android.assignment.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.api.model.AstronomyPicture
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture
import com.adyen.android.assignment.ui.components.FavouriteAPODList
import com.adyen.android.assignment.ui.components.LatestAPODList
import com.adyen.android.assignment.ui.components.ReorderDialog
import com.adyen.android.assignment.ui.state.APODListState
import com.adyen.android.assignment.ui.viewmodel.APODViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun APODListScreen(
    viewModel: APODViewModel,
    onAPODClicked: (String, String, String, String) -> Unit
) {

    val apodsState by viewModel.apodsState.collectAsState()
    val favourites by viewModel.favourites.collectAsState()

    val orderByTitleState by viewModel.orderByTitleState.collectAsState()
    val orderByDateState by viewModel.orderByDateState.collectAsState()

    var dialogVisibility by rememberSaveable { mutableStateOf(false) }
    val showDialog = apodsState is APODListState.Success && dialogVisibility

    viewModel.getLocalAPODs()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_bar_title),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        floatingActionButton = {
            if (apodsState is APODListState.Success) {
                FloatingActionButton(
                    onClick = {
                        dialogVisibility = true
                    },
                    shape = RoundedCornerShape(18.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(all = 14.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            painter = painterResource(R.drawable.ic_reorder),
                            contentDescription = stringResource(R.string.icon_description),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        Text(
                            text = stringResource(R.string.reorder_list),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 6.dp)
        ) {
            HandleAPODsState(
                viewModel = viewModel,
                apodsState = apodsState,
                favourites = favourites,
                onAPODClicked = { title, date, explanation, url ->
                    onAPODClicked(title, date, explanation, url)
                },
                showDialog = showDialog,
                dialogVisibility = {
                    dialogVisibility = it
                },
                orderByTitleState = orderByTitleState,
                orderByDateState = orderByDateState
            )
        }
    }
}

@Composable
private fun HandleAPODsState(
    viewModel: APODViewModel,
    apodsState: APODListState,
    favourites: List<LocalAstronomyPicture>,
    onAPODClicked: (String, String, String, String) -> Unit,
    showDialog: Boolean,
    dialogVisibility: (Boolean) -> Unit,
    orderByTitleState: Boolean,
    orderByDateState: Boolean,
) {
    when (apodsState) {
        is APODListState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                )

            }
        }

        is APODListState.Success -> {
            apodsState.data?.let {
                Column {
                    val configuration = LocalConfiguration.current
                    val screenHeight = configuration.screenHeightDp
                    when (favourites.isNotEmpty()) {
                        true -> {
                            FavouriteAPODList(
                                apods = favourites,
                                onAPODClicked = { title, date, explanation, url ->
                                    onAPODClicked(title, date, explanation, url)
                                },
                                modifier = Modifier
                                    .heightIn(max = (screenHeight / 3.5).dp)
                            )
                        }

                        else -> {}
                    }

                    LatestAPODList(
                        apods = it,
                        onAPODClicked = { title, date, explanation, url ->
                            onAPODClicked(title, date, explanation, url)
                        },
                        modifier = Modifier
                    )
                }

            }
            if (showDialog) {
                ReorderDialog(
                    setOrderByTitle = {
                        viewModel.onOrderByTitleStateChanged(it)
                    },
                    setOrderByDate = {
                        viewModel.onOrderByDateStateChanged(it)
                    },
                    setDialogVisibility = { dialogVisibility(it) },
                    orderByTitleState = orderByTitleState,
                    orderByDateState = orderByDateState,
                    onApply = {
                        viewModel.getAPODs()
                    }
                )
            }
        }

        is APODListState.Error -> {
            ErrorScreen(
                message = apodsState.message.toString(),
                onRefresh = {
                    viewModel.getAPODs()
                },
                modifier = Modifier
            )
        }
    }
}


@Preview
@Composable
fun PreviewAPODListItem() {
    LatestAPODList(
        apods = listOf(
            AstronomyPicture(
                date = "2006-04-15",
                explanation = "In this stunning cosmic vista, galaxy M81 is on the left ...",
                title = "The milky whay and the Andromeda galaxy",
                url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
                hdurl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
                mediaType = "image",
                serviceVersion = "v1"
            ),
            AstronomyPicture(
                date = "2006-04-15",
                explanation = "In this stunning cosmic vista, galaxy M81 is on the left ...",
                title = "The milky wsay and the Andromeda galaxy",
                url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
                hdurl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
                mediaType = "image",
                serviceVersion = "v1"
            ),
            AstronomyPicture(
                date = "2006-04-15",
                explanation = "In this stunning cosmic vista, galaxy M81 is on the left ...",
                title = "The milky way and the Andrompeda galaxy",
                url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
                hdurl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
                mediaType = "image",
                serviceVersion = "v1"
            ),
        ),
        onAPODClicked = { _, _, _, _ -> },
        modifier = Modifier
    )
}