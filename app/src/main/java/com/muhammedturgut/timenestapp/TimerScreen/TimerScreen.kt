package com.muhammedturgut.timenestapp.TimerScreen

import android.os.CountDownTimer
import android.text.BoringLayout
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor
import com.muhammedturgut.timenestapp.ui.theme.TimerRowColor
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(
    item: List<TimerItem>,
    saveFunction: (TimerItem) -> Unit,
    updateFunction: (TimerItem) -> Unit,
    deleteFunction: (TimerItem) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var isRunning by remember { mutableStateOf(false) }
    var timerState by remember { mutableStateOf(false) }
    var timerStateOption by remember { mutableStateOf(false) }
    var backgroundTimer by remember { mutableStateOf(Color(0xFFFFFFFF)) }

    if (timerStateOption){
        LaunchedEffect(Unit) {
            var indeks=0
            while (timerStateOption) {
                backgroundTimer = if (backgroundTimer == Color(0xFFFFFFFF)) {
                    Color(0xFFFF0000)
                } else {
                    Color(0xFFFFFFFF)
                }
                delay(1500)
                indeks++
                if(indeks==10){
                    timerStateOption=false
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundTimer)
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
                    .padding(start = 20.dp, top = 16.dp, bottom = 8.dp),
                textAlign = TextAlign.Start
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {

               if(item.isNotEmpty()){
                  key(item[0]){
                      TimerCircle(item[0],timerState=timerState, deleteFunction,onTimerFinish = {
                          deleteFunction(item[0])
                          timerState=false
                          timerStateOption=true
                      })
                  }
               }
                else{
                   TimerCircle(TimerItem("",0,0,0,0),timerState=false, deleteFunction)
               }

            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.reboottimerbutton),
                    contentDescription = null,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable {
                            isRunning = false
                        }
                )

                Image(
                    painter = painterResource(
                        if(timerState) R.drawable.timerstatetwobutton
                        else R.drawable.timerstatebutton
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable {
                            timerState = !timerState
                            isRunning = !isRunning
                        }
                )


                Image(
                    painter = painterResource(R.drawable.timeraddbutton),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable {
                            showDialog = true
                        }
                )

                if (showDialog) {
                    TimerAddDialog(onDismiss = { showDialog = false }, saveFunction)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 38.dp)
            ) {
                LazyColumn {
                    items(item) { timer ->
                        TimerRow(timer, deleteFunction)
                    }
                }
            }
        }
    }
}

@Composable
fun TimerRow(timer: TimerItem, deleteFunction: (TimerItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(TimerRowColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${timer.timerOrder}",
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            fontSize = 18.sp, textAlign = TextAlign.Center
        )

        Text(
            text = timer.timerName ?: "",
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = "${String.format("%02d", timer.timer_hour)} : ${String.format("%02d", timer.timer_minute)} : ${String.format("%02d", timer.timer_second)}",
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp, textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(R.drawable.rowdelete),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                .clickable {
                    deleteFunction(timer)
                }
        )
    }
}

@Composable
fun TimerCircle(
    totalTime: TimerItem,
    timerState:Boolean,
    deleteFunction: (TimerItem) -> Unit,
    onTimerFinish: () -> Unit = {},
    modifier: Modifier = Modifier.size(200.dp)
) {
    var remainingHour by remember { mutableStateOf(totalTime.timer_hour) }
    var remainingMinute by remember { mutableStateOf(totalTime.timer_minute) }
    var remainingSecond by remember { mutableStateOf(totalTime.timer_second) }
    var remainingTime by remember { mutableStateOf(totalTime.toLong()) }
    val totalMillis = totalTime.toLong()

    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    LaunchedEffect(timerState) {
        if (timerState) {
            timer?.cancel() // Var olan zamanlayıcıyı iptal et
            timer = object : CountDownTimer(remainingTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTime = millisUntilFinished
                    remainingHour = (millisUntilFinished / 3600000).toInt()
                    remainingMinute = ((millisUntilFinished % 3600000) / 60000).toInt()
                    remainingSecond = ((millisUntilFinished % 60000) / 1000).toInt()
                }

                override fun onFinish() {
                    remainingTime = 0
                    remainingHour = 0
                    remainingMinute = 0
                    remainingSecond = 0
                    deleteFunction(totalTime)
                    onTimerFinish()
                }
            }
            timer?.start()
        } else {
            timer?.cancel() // Zamanlayıcı çalışmıyorsa iptal et
        }
    }

    val progress = (remainingTime / totalMillis.toFloat())

    Box(modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 28f
            val radius = size.minDimension / 2 - strokeWidth

            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f),
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                color = PrimaryColor,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${remainingHour.toString().padStart(2, '0')} : ${remainingMinute.toString().padStart(2, '0')} : ${remainingSecond.toString().padStart(2, '0')}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TimerAddDialog(onDismiss: () -> Unit, saveFunction: (TimerItem) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(4.dp),
            color = Color.White
        ) {
            var order by remember { mutableStateOf(0) }
            var name by remember { mutableStateOf("") }

            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryColor),
                    Alignment.Center
                ){
                    Text(text="Süre Tut",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp, textAlign = TextAlign.Center,
                        color = Color.White)
                }

                val listTimer = RotaryKnobb()

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Görev Adı", fontSize = 12.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    textStyle = TextStyle(textAlign = TextAlign.Center) // Metni ortalamak için textAlign kullanılıyor
                )

                Button(
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        saveFunction(
                            TimerItem(name, listTimer[0], listTimer[1], listTimer[2], order)
                        )
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                ) {
                    Text("Ekle")
                }
            }
        }
    }
}

@Composable
fun RotaryKnobb(): List<Int> {

    var saat by remember { mutableStateOf(0) }
    var dakika by remember { mutableStateOf(0) }
    var saniye by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .size(300.dp, 140.dp)
            .padding(horizontal = 34.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.size(48.dp, 140.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.increasebutton),
                contentDescription = null,
                modifier = Modifier
                    .size(46.dp)
                    .clickable {
                        saat++
                        saat = (saat + 24) % 24
                    }
            )
            Text(
                text = String.format("%02d", saat),
                textAlign = TextAlign.Center,
                modifier = Modifier,
                color = Color.Black,
                fontSize = 28.sp
            )
            Image(
                painter = painterResource(id = R.drawable.reducebutton),
                contentDescription = null,
                modifier = Modifier
                    .size(46.dp)
                    .clickable {
                        saat--
                        saat = (saat + 24) % 24
                    }
            )
        }

        Text(
            text = ":",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Center,
        )

        Column(
            modifier = Modifier.size(48.dp, 140.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.increasebutton),
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clickable {
                        dakika++
                        dakika = (dakika + 60) % 60
                    }
            )
            Text(
                text = String.format("%02d", dakika),
                textAlign = TextAlign.Center,
                modifier = Modifier,
                color = Color.Black,
                fontSize = 24.sp
            )
            Image(
                painter = painterResource(id = R.drawable.reducebutton),
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clickable {
                        dakika--
                        dakika = (dakika + 60) % 60
                    }
            )
        }

        Text(
            text = ":",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Center,
        )

        Column(
            modifier = Modifier.size(30.dp, 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.increasebutton),
                contentDescription = null,
                modifier = Modifier
                    .size(38.dp)
                    .clickable {
                        saniye++
                        saniye = (saniye + 60) % 60
                    }
            )
            Text(
                text = String.format("%02d", saniye),
                textAlign = TextAlign.Center,
                modifier = Modifier,
                color = Color.Black,
                fontSize = 20.sp
            )
            Image(
                painter = painterResource(id = R.drawable.reducebutton),
                contentDescription = null,
                modifier = Modifier
                    .size(38.dp)
                    .clickable {
                        saniye--
                        saniye = (saniye + 60) % 60
                    }
            )
        }
    }

    return listOf(saat, dakika, saniye)
}

