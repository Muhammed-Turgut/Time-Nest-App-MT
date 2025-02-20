import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import com.muhammedturgut.timenestapp.ModelClass.Item
import com.muhammedturgut.timenestapp.ui.theme.GolasRowColor
import com.muhammedturgut.timenestapp.ui.theme.GolasRowColorText
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme

@Composable
fun GoalsScreen(initialItemList: List<Item>, saveFunction: (item: Item) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var itemList by remember { mutableStateOf(initialItemList) }
    var isLoading by remember { mutableStateOf(true) }




        // Simulate data loading delay
        LaunchedEffect(Unit) {
            delay(3000) // 3 seconds delay
            isLoading = false
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {},
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FAB {
                    showDialog = true
                }
            },
            containerColor = Color.White,
            content = { innerPadding ->
                if (isLoading) {
                    // Show loading indicator
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryColor)
                    }
                } else {
                    // Show LazyColumn with items
                    LazyColumn(
                        contentPadding = innerPadding,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        items(itemList) { item ->
                            GoalsROW(item = item)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        )



    if (showDialog) {
        CustomDialog(onDismiss = { showDialog = false }, saveFunction = { item ->
            itemList = itemList + item // itemList'i güncelleyin
            saveFunction(item)
            showDialog = false
        })


    }
}

@Composable
fun GoalsROW(item: Item) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GolasRowColor)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.missionName ?: "",
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp, top = 8.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Image(
                painter = painterResource(com.muhammedturgut.timenestapp.R.drawable.rowdelete),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        // item silme bildirimi
                    }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Başlama Tarihi: ${item.startTime}",
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp, top = 8.dp),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                color = GolasRowColorText,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = "Bitiş Tarihi: ${item.endTime}",
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp, top = 8.dp),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                color = GolasRowColorText,
                fontWeight = FontWeight.Normal
            )

            Image(
                painter = painterResource(com.muhammedturgut.timenestapp.R.drawable.rowedit),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        // item düzenleme bildirimi
                    }
            )
        }
    }
}

@Composable
fun FAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = PrimaryColor
    ) {
        Icon(Icons.Filled.Add, contentDescription = null, tint = Color.White)
    }
}

@Composable
fun CustomDialog(onDismiss: () -> Unit, saveFunction: (item: Item) -> Unit) {
    val itemName = remember { mutableStateOf("") }
    val startTime = remember { mutableStateOf("") }
    val endTime = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = itemName.value,
                    placeholder = { Text(text = "Görev") },
                    onValueChange = { itemName.value = it }
                )
                TextField(
                    value = startTime.value,
                    placeholder = { Text(text = "Başlama Tarihi") },
                    onValueChange = { startTime.value = it }
                )
                TextField(
                    value = endTime.value,
                    placeholder = { Text(text = "Bitiş Tarihi") },
                    onValueChange = { endTime.value = it }
                )
                Button(onClick = {
                    val itemToInsert = Item(
                        missionName = itemName.value,
                        startTime = startTime.value,
                        endTime = endTime.value
                    )
                    saveFunction(itemToInsert)
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}