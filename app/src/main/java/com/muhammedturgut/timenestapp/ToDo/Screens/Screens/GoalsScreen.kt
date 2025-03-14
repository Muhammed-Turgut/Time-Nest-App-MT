package com.muhammedturgut.timenestapp.ToDo.Screens.Screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Calendar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor
import com.muhammedturgut.timenestapp.ui.theme.RowDarkBlue
import com.muhammedturgut.timenestapp.ui.theme.RowOrange
import com.muhammedturgut.timenestapp.ui.theme.RowRed
import com.muhammedturgut.timenestapp.ui.theme.RowYellow
import kotlin.random.Random

@Composable
fun GoalsScreen(item: List<Item>, saveFunction: (Item) -> Unit, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onTertiaryContainer)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(item) { currentItem ->
                GoalsROW(
                    item = currentItem,
                    UpdateFuncition = UpdateFuncition,
                    deleteItem = deleteItem
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FAB { showDialog = true }
        }

        AnimatedVisibility(visible = showDialog, modifier = Modifier.fillMaxWidth()) {
            CustomDialog(
                onDismiss = { showDialog = false },
                saveFunction = { newItem ->
                    saveFunction(newItem)
                    showDialog = false
                }
            )
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalsROW(item: Item, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit) {

    var isLongPressed by remember { mutableStateOf(false) } // Basılı tutulduğunda değişecek
    var isCompleted by remember { mutableStateOf(false) }

        val cardWidth by animateDpAsState(
            targetValue = if (isCompleted || isLongPressed ) 370.dp else 400.dp, // Dp.Unspecified yerine sabit değer kullanıldı
            animationSpec = tween(durationMillis = 300),
            label = "CardWidthAnimation"
        )





        val listRowColor = listOf(RowYellow, RowDarkBlue, RowOrange, RowRed)
        val randomColor = remember { listRowColor[Random.nextInt(listRowColor.size)] } // Tekrar seçilmesini önlemek için remember eklendi

        Card(
            modifier = Modifier
                .width(cardWidth)// Başlangıç değeri artık kesin
                .combinedClickable(
                    onClick = {
                        isLongPressed=false
                        isCompleted = !isCompleted
                    }, // Normal tıklamaya işlem koymadık, sadece uzun basınca açılacak
                    onLongClick = {
                        isCompleted=false
                        isLongPressed = !isLongPressed // Basılı tutulduğunda genişlik değişir
                    })
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
                        .background(randomColor)
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
                                    color = randomColor,
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

                if (isLongPressed || isCompleted) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(40.dp)
                            .background(if(isLongPressed) Color.Red else Color.Green)
                            .clickable {
                                if(isCompleted){
                                    item.State= 0
                                    UpdateFuncition(item)
                                }
                                else{
                                    deleteItem(item)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isCompleted){
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Tamamlandı",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        else{
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Tamamlandı",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }


@Composable
fun CustomDialog(onDismiss: () -> Unit, saveFunction: (Item) -> Unit) {
    var itemName by remember { mutableStateOf("") }
    var itemAbout by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val currentDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    var startTime by remember { mutableStateOf(currentDate) }
    var endTime by remember { mutableStateOf("") }

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
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Görev Adı") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = itemAbout,
                    onValueChange = { itemAbout = it },
                    label = { Text("Görev Hakkında") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Başlama Tarihi") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { startDatePickerDialog.show() },
                    readOnly = true
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
                            if (itemName.isNotBlank()) {
                                saveFunction(
                                    Item(
                                        missionName = itemName,
                                        missionAbout = itemAbout,
                                        startTime = startTime,
                                        endTime = endTime,
                                        State = 1
                                    )
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

@Composable
fun FAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = PrimaryColor
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
    }
}