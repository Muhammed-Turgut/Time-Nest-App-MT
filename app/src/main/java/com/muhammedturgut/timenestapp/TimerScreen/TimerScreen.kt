package com.muhammedturgut.timenestapp.TimerScreen

import android.os.CountDownTimer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem
import com.muhammedturgut.timenestapp.ui.theme.TimerCricelColor
import com.muhammedturgut.timenestapp.ui.theme.TimerRowColor


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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Timer",
                fontFamily = FontFamily(Font(R.font.righteousregular)),
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                modifier = Modifier
                    .padding(start = 20.dp, top = 16.dp, bottom = 8.dp), textAlign = TextAlign.Start
            )

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
                TimerCircle(totalTime = 10_000)
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                Image(painter = painterResource(R.drawable.trashbutton), contentDescription = null, modifier = Modifier.size(38.dp))
                Image(painter = painterResource(R.drawable.timerstatebutton), contentDescription = null, modifier = Modifier.padding(start = 8.dp).size(48.dp))
                Image(painter = painterResource(R.drawable.timeraddbutton), contentDescription = null, modifier = Modifier.padding(start = 8.dp).size(38.dp))
            }

            Box(modifier = Modifier.fillMaxSize().padding(top = 38.dp)){

                LazyColumn {
                    items(listTimer){ timer ->
                        TimerRow(timer)
                    }
                }
            }


        }
    }
}

@Composable
fun TimerRow(timer:TimerItem){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .padding(top = 16.dp)
        .clip(RoundedCornerShape(30.dp))
        .background(TimerRowColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){

        Text(text=timer.timerOrder ?: "",
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            fontSize = 18.sp, textAlign = TextAlign.Center) //Sıraya alınan timer numarası

        Text(text=timer.timerName ?: "",
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp,top = 8.dp, bottom = 8.dp),
            fontSize = 18.sp,
            textAlign = TextAlign.Center)

        Text(text=timer.timerAmount ?: "",
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,textAlign = TextAlign.Center)

        Image(painter = painterResource(R.drawable.rowdelete),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp,top = 8.dp, bottom = 8.dp)
        )
    }
}


@Composable
fun TimerCircle(
    totalTime: Int = 10_000,  // Toplam süre (10 saniye)
    modifier: Modifier = Modifier.size(200.dp)
) {
    var remainingTime by remember { mutableStateOf(totalTime) }
    val progress by remember { derivedStateOf { remainingTime.toFloat() / totalTime } }

    LaunchedEffect(Unit) {
        val timer = object : CountDownTimer(totalTime.toLong(), 100) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                remainingTime = 0
            }
        }
        timer.start()
    }

    Box(modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 28f
            val radius = size.minDimension / 2 - strokeWidth

            // Arka plan çemberi
            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f),
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Kalan süreye göre çember
            drawArc(
                color = TimerCricelColor,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Ortada kalan süre
        Text(
            text = "${(remainingTime / 1000)}",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


val listTimer= listOf<TimerItem>(
    TimerItem("Kimya","05:00","1"),
    TimerItem("Matemetik","20:00","2"),
    TimerItem("Fizik","30:00","3"),
    TimerItem("Biyoloji","10:00","4"),
    TimerItem("Edebiyat","12:00","5"),
    TimerItem("Tarih","03:00","6"),
    TimerItem("Fizik","30:00","3"),
    TimerItem("Biyoloji","10:00","4"),
    TimerItem("Edebiyat","12:00","5"),
    TimerItem("Tarih","03:00","6"),
    TimerItem("Fizik","30:00","3"),
    TimerItem("Biyoloji","10:00","4"),
    TimerItem("Edebiyat","12:00","5"),
    TimerItem("Tarih","03:00","6"))