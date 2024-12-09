package com.bedrock.encryptednotes.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AuthFailedScreen() {
    MemoTheme {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = "Authentication failed. Please close and re-open app to try to authenticate again.",
                fontSize = 24.sp,
                style = MaterialTheme.typography.body1
            )
        }
    }
}