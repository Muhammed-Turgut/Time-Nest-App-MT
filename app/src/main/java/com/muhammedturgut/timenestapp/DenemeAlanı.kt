package com.muhammedturgut.timenestapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColumCalender() {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
     ChainBreakingRoww()
    }
}


@Composable
fun ChainBreakingRoww() {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(16.dp)
            .shadow(
                elevation = 8.dp,
                ambientColor = Color.Black,
                spotColor = Color.Black,
                shape = RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color(0xFFF7F7F7)),
        horizontalAlignment = Alignment.Start // Tüm öğeleri sola yaslamak için
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(R.drawable.chainbreakingicon),
                contentDescription = null)
            Text(
                text = "Chain Name",
                modifier = Modifier
                    .weight(1f),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
        }
        Text(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap ",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)// Metni sola yaslamak için
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Durumu: Devam ediyor",// Metni sola yaslamak için
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
            Image(painter = painterResource(R.drawable.trashchanrow),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 12.dp, end = 16.dp)
                    .size(38.dp))

        }

    }
}



/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ColumCalender()
}*/