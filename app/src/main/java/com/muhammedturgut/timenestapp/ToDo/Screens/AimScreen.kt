package com.muhammedturgut.timenestapp.ToDo.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.ui.theme.GolasRowColorText
import com.muhammedturgut.timenestapp.ui.theme.RowOrange
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlinx.coroutines.launch

@Composable
fun AimScreen(item: List<Item>, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onTertiaryContainer))
    {
        Row (modifier = Modifier.fillMaxSize()){

            val days = generateDaysList()
            val today = Calendar.getInstance() // Mevcut tarihi alın

            // LazyListState ve CoroutineScope'u hatırla
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            // Takvim kısmını tutacak yer
            LazyColumn(
                state = listState,
                modifier = Modifier.width(60.dp)
            ) {
                items(days) { day ->
                    CalenderRow(day = day, isToday = isSameDay(day, today)) // isToday parametresi geçiliyor
                }
            }

            LaunchedEffect(Unit) {
                // Mevcut günü bul ve LazyColumn'a kaydır
                val todayIndex = days.indexOfFirst { isSameDay(it, today) }
                if (todayIndex != -1) {
                    coroutineScope.launch {
                        listState.scrollToItem(todayIndex)
                    }
                }
            }

            // Veri tabanı verilerin geleceği yer
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(item) { currentItem ->
                    AimROW(item=currentItem, UpdateFuncition,deleteItem)
                }
            }
        }

    }
}

@Composable
fun CalenderRow(day: CalendarDay, isToday: Boolean){

    val backgroundColor = if (isToday) Color(0xFFFFA500) else Color(0xFF4A90E2) // Bugünse turuncu, değilse mavi

    Surface(
        modifier = Modifier
            .size(100.dp)
            .padding(top = 8.dp), // Dairenin boyutu
        color = backgroundColor, // Renk duruma göre ayarlanıyor
        shape = CircleShape // Dairesel şekil
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly // Metinleri eşit dağıt
        ) {
            Text(
                text = day.monthName, // "Mar"
                textAlign = TextAlign.Center,
                fontSize = 16.sp, // Daha büyük font
                fontWeight = FontWeight.Light,
                color = Color.White
            )

            Text(
                text = "%02d".format(day.dayOfMonth), // "09"
                textAlign = TextAlign.Center,
                fontSize = 24.sp, // En büyük font
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = day.dayName, // "Pzt"
                textAlign = TextAlign.Center,
                fontSize = 16.sp, // Büyük font
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}

fun isSameDay(day: CalendarDay, today: Calendar): Boolean {
    val todayDay = today.get(Calendar.DAY_OF_MONTH)
    val todayMonth = today.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("tr"))
    val todayDayName = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale("tr"))

    return day.dayOfMonth == todayDay && day.monthName == todayMonth && day.dayName == todayDayName
}


//Takvim için yılları alıyoruz.
fun generateDaysList(): List<CalendarDay> {
    val daysList = mutableListOf<CalendarDay>()
    val calendar = Calendar.getInstance()

    // Mevcut yılı al
    val currentYear = calendar.get(Calendar.YEAR)
    // 1 Ocak mevcut yıldan başla
    calendar.set(currentYear, Calendar.JANUARY, 1)

    val monthFormat = SimpleDateFormat("MMM", Locale("tr")) // "Ocak", "Şubat"
    val dayFormat = SimpleDateFormat("EEE", Locale("tr")) // "Pazartesi"

    repeat(365) { // 365 gün ekle
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) // Ayın kaçıncı günü
        val monthName = monthFormat.format(calendar.time) // Hangi ay
        val dayName = dayFormat.format(calendar.time) // Haftanın günü

        daysList.add(CalendarDay(dayOfMonth, monthName, dayName))
        calendar.add(Calendar.DAY_OF_MONTH, 1) // Bir sonraki güne geç
    }

    return daysList
}


@Composable
fun AimROW(item: Item, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()// Başlangıç değeri artık kesin
            .padding(top = 6.dp, bottom = 10.dp)
            .padding(horizontal = 4.dp)
            .shadow(
                elevation = 8.dp,
                ambientColor = Color.Black,
                spotColor = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .wrapContentHeight(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .background(MaterialTheme.colorScheme.surfaceTint)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(9.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(RowOrange)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Canvas(modifier = Modifier.size(10.dp)) {
                            drawCircle(
                                color = RowOrange,
                                radius = 10f
                            )
                        }

                        Text(
                            text = item.missionName ?: "",
                            modifier = Modifier.padding(start = 6.dp).weight(1f),
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontFamily = FontFamily(Font(R.font.sorafont)),
                            fontWeight = FontWeight.SemiBold
                        )

                        Image(
                            painter = painterResource(R.drawable.roweditt),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    Text(
                        text = item.missionAbout ?: "",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                        fontSize = 6.sp,
                        fontFamily = FontFamily(Font(R.font.sorafont)),
                        fontWeight = FontWeight.Light,
                        lineHeight = TextUnit(8f, TextUnitType.Sp)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Başlama Tarihi: ${item.startTime}",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 10.dp).weight(1f),
                        fontFamily = FontFamily(Font(R.font.sorafont)),
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    Text(
                        text = "Bitiş Tarihi: ${item.endTime}",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 10.dp, end = 24.dp),
                        fontFamily = FontFamily(Font(R.font.sorafont)),
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

// Günleri oluşturan veri modeli
data class CalendarDay(val dayOfMonth: Int, val monthName: String, val dayName: String, )

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimeNestAppTheme {

    }
}