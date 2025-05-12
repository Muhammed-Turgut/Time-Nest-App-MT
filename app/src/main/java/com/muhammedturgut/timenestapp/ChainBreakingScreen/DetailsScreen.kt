package com.muhammedturgut.timenestapp.ChainBreakingScreen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemDetailChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.ChainViewModel
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.DateTime
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.DayInfo
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.MonthWithDays
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.ToDo.Screens.Screens.CustomDialog
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor
import kotlinx.coroutines.delay
import java.time.MonthDay
import java.util.Calendar
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreenChainBreaking(navHostController: NavHostController,
                               viewModel: ChainViewModel= viewModel(),
                               notId:Int?,


                               saveFunction:(ItemDetailChain) -> Unit) {

    var showDialog by remember { mutableStateOf(false) }

    //Burda uygun olan item aldık.
    val item = remember { mutableStateOf<ItemChain?>(null) }
    LaunchedEffect(notId) {
        item.value = viewModel.getItemNot(notId ?: 1)
    }
    LaunchedEffect(Unit) {
        viewModel.getItemListDetails(notId ?: 1)
    }

    val itemsDetailsList by viewModel.itemListDetailsChain.collectAsState(initial = emptyList())

    var montIndex by remember { mutableStateOf(viewModel.nowMonth()) }
    val monthDay = viewModel.getMonthData(montIndex)



    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Column(modifier = Modifier.padding(top = 16.dp)) {

                IconButton(
                    onClick = { navHostController.navigate("ChainBreaking") },
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.backbutton),
                        contentDescription = "Back"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.chainbreakingicon),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${item.value?.chainName}",
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.righteousregular))
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = "${item.value?.chainAbout}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp)
                                .align(alignment = Alignment.Bottom)
                                .clickable {
                                    montIndex = (montIndex - 1) % 12
                                }
                        )

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${viewModel.getYearWithDays().year}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = "${monthDay.monthName}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                        }


                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp)
                                .align(alignment = Alignment.Bottom)
                                .clickable {
                                    montIndex = (montIndex + 1) % 12
                                }
                        )
                    }
                    DetailsDays(monthDay)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {

                    Text(
                        text = "Notlar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        Modifier.size(28.dp)
                            .clickable {
                                showDialog = true
                            })
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(start = 16.dp, top = 4.dp, end = 16.dp)
                ) {
                    items(itemsDetailsList) { item ->
                            DetailsRowDay(item, delete = { itemin -> viewModel.deleteItemDetail(itemin,notId?:1)})
                    }
                }
            }
            AnimatedVisibility(visible = showDialog, modifier = Modifier.fillMaxWidth()) {
                addDialog(
                    onDismiss = { showDialog = false },
                    save = { newItem ->
                        saveFunction(newItem)
                        showDialog = false
                    },
                    notId = notId ?: 1
                )
            }
        }
    }
}

@Composable
fun DetailsDays(monthDay: MonthWithDays) {
    var selectedDay by remember { mutableStateOf<DayInfo?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(monthDay.days.chunked(8)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                rowItems.forEach { day ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Eğer bu gün tıklanmışsa, üstüne dialog göster
                        if (selectedDay == day) {
                            LaunchedEffect(key1 = selectedDay) {
                                delay(3000) // 3 saniye bekle
                                selectedDay = null // sonra kapat
                            }
                            DayStartDialog()
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        DetailDaysRow(
                            dayNameNumber = day,
                            onClick = {
                                selectedDay = if (selectedDay == day) null else day
                            }
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun DetailDaysRow(dayNameNumber: DayInfo,onClick:() -> Unit){

    Box(modifier = Modifier
        .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
        .size(width = 35.dp, height = 46.dp)
        .clip(RoundedCornerShape(3.dp))
        .background(Color(0xFFB4B4B4))
        .clickable{
            onClick()
        }){

        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            Text("${dayNameNumber.dayNumber}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White, lineHeight = 10.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text("${dayNameNumber.dayOfWeek ?:"Pzt"}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                lineHeight = 10.sp)

        }
    }

}

@Composable
fun DayStartDialog() {
    Box(
        modifier = Modifier
            .size(width = 112.dp, height = 50.dp)
            .background(Color(0xFFF6F6F6), shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Onay Butonu
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF57DA37)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_check_24),
                    contentDescription = "Onay",
                    modifier = Modifier.size(20.dp)
                )
            }

            // Reddetme Butonu
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFFE2D2D)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_close_24),
                    contentDescription = "Reddet",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun DetailsRowDay(item:ItemDetailChain,delete:(ItemDetailChain) -> Unit){
    Column(modifier = Modifier
        .padding(start = 4.dp, end = 4.dp, top = 8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(color = Color(0x0FC7C7C7))){
        Row(modifier = Modifier.fillMaxWidth()){

            Text(text="${item.notDate}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 16.sp,
                modifier = Modifier.weight(1f))

            Image(painter = painterResource(R.drawable.rowdelete),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
                    .clickable{
                        delete(item)
                    })
        }

    Text(text="${item.notAbout}",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 16.sp)
   }
}

@Composable
fun addDialog(save:(ItemDetailChain) -> Unit, onDismiss: () -> Unit,notId:Int){

        var itemAbout by remember { mutableStateOf("") }
        val calendar = Calendar.getInstance()
        val currentDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
        var startTime by remember { mutableStateOf(currentDate) }

        val context = LocalContext.current

        val startDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                startTime = "$dayOfMonth/${month + 1}/$year"
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        )

        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    TextField(
                        value = itemAbout,
                        onValueChange = { itemAbout = it },
                        label = { Text("Görev Hakkında") },
                        modifier = Modifier.fillMaxWidth()
                    )



                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                        ) {
                            Text("İptal")
                        }

                        Button(
                            onClick = {
                                if (itemAbout.isNotBlank()) {
                                    save(
                                        ItemDetailChain(notAbout =itemAbout, notDate = startTime, notOwnerId = notId)
                                    )
                                    onDismiss()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                        ) {
                            Text("Kaydet")
                        }
                    }
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun displayMaterial(){
    DayStartDialog()
}