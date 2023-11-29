package com.example.reto9.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reto9.R

@Composable
fun MainAppBar(
    label: String = stringResource(id = R.string.app_name),
    leadingIcon: ImageVector = Icons.Default.Menu,
    trailingIcon: ImageVector = Icons.Default.Info,
    hideTrailingIcon: Boolean = false,
    leadingAction: () -> Unit = {},
    trailingAction: () -> Unit = {},
) {
    val fontSize = 24.sp
    val contentModifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
        .padding(4.dp)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
        ),
        modifier = contentModifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = leadingAction
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = leadingIcon,
                    contentDescription = "icon",
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = fontSize,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                text = label
            )
            Box(modifier = Modifier.weight(1f)) {
                if (!hideTrailingIcon) IconButton(
                    onClick = trailingAction
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = trailingIcon,
                        contentDescription = "icon",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainAppBarPreview() {
    MainAppBar()
}