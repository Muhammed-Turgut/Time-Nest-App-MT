package com.muhammedturgut.timenestapp

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammedturgut.timenestapp.ui.theme.RowYellow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun ColumCalender(){
Box(modifier = Modifier.fillMaxSize().background(Color.White))


TakvimEkrani()


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    ColumCalender()

}


@Composable
fun TakvimEkrani() {
    val days = generateDaysList() // Günleri oluştur

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(days) { day ->
            TakvimItem(day)
        }
    }
}

@Composable
fun TakvimItem(day: CalendarDay) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day.date, // "1 Mart", "2 Mart" gibi
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = day.dayName) // "Pazartesi", "Salı" gibi
    }
}

// Günleri oluşturan veri modeli
data class CalendarDay(val date: String, val dayName: String)

// Örnek veri üretme fonksiyonu
fun generateDaysList(): List<CalendarDay> {
    val daysList = mutableListOf<CalendarDay>()
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("d MMMM", Locale("tr"))
    val dayFormat = SimpleDateFormat("EEEE", Locale("tr"))

    repeat(30) { // Örnek olarak 30 gün
        val date = dateFormat.format(calendar.time)
        val dayName = dayFormat.format(calendar.time)
        daysList.add(CalendarDay(date, dayName))
        calendar.add(Calendar.DAY_OF_MONTH, 1) // Bir sonraki güne geç
    }
    return daysList
}
