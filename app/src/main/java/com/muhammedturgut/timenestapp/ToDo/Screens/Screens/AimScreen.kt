package com.muhammedturgut.timenestapp.ToDo.Screens.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.muhammedturgut.timenestapp.ui.theme.RowOrange
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme
import java.util.Calendar
import java.util.Locale
import kotlinx.coroutines.launch

import java.text.SimpleDateFormat
import java.util.*

data class CalendarDay(
    val dayOfMonth: Int,
    val monthName: String,
    val dayName: String,
    val date: Date // EKLENDİ
)

@Composable
fun AimScreen(item: List<Item>, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onTertiaryContainer)) {

        Row(modifier = Modifier.fillMaxSize()) {

            val days = generateDaysList()
            var filterList by remember { mutableStateOf("") }
            var selectedDate by remember { mutableStateOf("") }

            val today = Calendar.getInstance()
            val todayFormatted = SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(today.time)

            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            LazyColumn(
                state = listState,
                modifier = Modifier.width(60.dp)
            ) {
                items(days) { day ->
                    val formattedDate = SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(day.date)

                    CalenderRow(
                        day = day,
                        isToday = isSameDay(day, today),
                        isSelected = formattedDate == selectedDate, // tıklanan günün kontrolü
                        updateListDay = { dateString ->
                            filterList = dateString
                            selectedDate = dateString // tıklanan günü güncelle
                        }
                    )
                }
            }

            LaunchedEffect(Unit) {
                val todayIndex = days.indexOfFirst { isSameDay(it, today) }
                if (todayIndex != -1) {
                    coroutineScope.launch {
                        listState.scrollToItem(todayIndex)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(item) { currentItem ->
                    if (filterList == ""){
                        if(currentItem.endTime == todayFormatted){ // Tarih karşılaştırması
                            AimROW(item=currentItem, UpdateFuncition,deleteItem)
                        }
                    }
                    else{
                        if (currentItem.endTime == filterList) {
                            AimROW(item = currentItem, UpdateFuncition, deleteItem)
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun CalenderRow(
    day: CalendarDay,
    isToday: Boolean,
    isSelected: Boolean,
    updateListDay: (String) -> Unit
) {
    val backgroundColor = when {
        isSelected -> Color(0xFF2ECC71) // Seçili gün: yeşil
        isToday -> Color(0xFFFFA500)   // Bugün: turuncu
        else -> Color(0xFF4A90E2)      // Normal gün: mavi
    }

    Surface(
        modifier = Modifier
            .size(100.dp)
            .padding(top = 8.dp, start = 8.dp)
            .clip(CircleShape)
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                val formattedDate = SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(day.date)
                updateListDay(formattedDate)
            },
        color = backgroundColor,
        shape = CircleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .border(2.dp, Color(0xFFFFFFFF),CircleShape),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = day.monthName,
                textAlign = TextAlign.Center,
                lineHeight = 10.sp,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = Color.White,
            )

            Text(
                text = "%02d".format(day.dayOfMonth),
                textAlign = TextAlign.Center,
                lineHeight = 10.sp,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            Text(
                text = day.dayName,
                lineHeight = 10.sp,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,

            )
        }
    }
}


fun isSameDay(day: CalendarDay, today: Calendar): Boolean {
    val todayFormatted = SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(today.time)
    val dayFormatted = SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(day.date)
    return todayFormatted == dayFormatted
}

fun generateDaysList(): List<CalendarDay> {
    val daysList = mutableListOf<CalendarDay>()
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    calendar.set(currentYear, Calendar.JANUARY, 1)

    val monthFormat = SimpleDateFormat("MMM", Locale("tr"))
    val dayFormat = SimpleDateFormat("EEE", Locale("tr"))

    repeat(365) {
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val monthName = monthFormat.format(calendar.time)
        val dayName = dayFormat.format(calendar.time)
        val date = calendar.time

        daysList.add(CalendarDay(dayOfMonth, monthName, dayName, date))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
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
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.sorafont)),
                        fontWeight = FontWeight.Light,
                        lineHeight = TextUnit(8f, TextUnitType.Sp)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
                    Text(
                        text = "Başlama Tarihi:${item.startTime}",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 6.dp, end = 8.dp),
                        fontFamily = FontFamily(Font(R.font.sorafont)),
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    Text(
                        text = "Bitiş Tarihi:${item.endTime}",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        fontFamily = FontFamily(Font(R.font.sorafont)),
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimeNestAppTheme {

    }
}