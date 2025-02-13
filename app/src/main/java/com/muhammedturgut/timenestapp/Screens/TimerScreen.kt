package com.muhammedturgut.timenestapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammedturgut.timenestapp.R


@Composable
fun TimerScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Time Nest",
                fontFamily = FontFamily(Font(R.font.righteousregular)),
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(20.dp, 24.dp, 8.dp, 0.dp)
            )

        }
    }
}