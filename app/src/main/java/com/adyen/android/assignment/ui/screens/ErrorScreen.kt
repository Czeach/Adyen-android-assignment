package com.adyen.android.assignment.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R


@Composable
fun ErrorScreen(
    message: String,
    onRefresh: () -> Unit,
    modifier: Modifier
) {
    Surface (
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxSize()
            .padding(all = 32.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(160.dp),
                painter = painterResource(R.drawable.ic_vector),
                contentDescription = stringResource(R.string.icon_description),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(
                modifier = Modifier
                    .height(120.dp)
            )
            Text(
                text = message,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
            )
            Text(
                text = stringResource(R.string.refresh_prompt),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(
                modifier = Modifier
                    .height(28.dp)
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(6.dp),
                onClick = { onRefresh() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 58.dp)
            ) {
                Text(
                    text = stringResource(R.string.refresh),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewError() {
    ErrorScreen(
        message = "Oops! Something went wrong.",
        onRefresh = {},
        modifier = Modifier
    )
}