package com.adyen.android.assignment.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReorderDialog(
    onApply: () -> Unit,
    setOrderByTitle: (Boolean) -> Unit,
    setOrderByDate: (Boolean) -> Unit,
    orderByTitleState: Boolean,
    orderByDateState: Boolean,
    setDialogVisibility: (Boolean) -> Unit
) {

    var orderByTitleToggle by rememberSaveable { mutableStateOf(orderByTitleState) }
    var orderByDateToggle by rememberSaveable { mutableStateOf(orderByDateState) }

    BasicAlertDialog(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        onDismissRequest = {
            setDialogVisibility(false)
        },
        content = {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                shape = RoundedCornerShape(6.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 22.dp, vertical = 28.dp)
                ) {
                    Text(
                        text = stringResource(R.string.reorder_list),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.reorder_by_title),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        RadioButton(
                            selected = orderByTitleToggle,
                            onClick = {
                                orderByTitleToggle = !orderByTitleToggle
                                if (orderByTitleToggle) orderByDateToggle = false
                            },
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.reorder_by_date),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        RadioButton(
                            selected = orderByDateToggle,
                            onClick = {
                                orderByDateToggle = !orderByDateToggle
                                if (orderByDateToggle) orderByTitleToggle = false
                            }
                        )
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(6.dp),
                        onClick = {
                            setOrderByDate(orderByDateToggle)
                            setOrderByTitle(orderByTitleToggle)
                            setDialogVisibility(false)
                            onApply()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.apply),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        shape = RoundedCornerShape(6.dp),
                        onClick = {
                            orderByDateToggle = false
                            orderByTitleToggle = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.reset),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    )
}