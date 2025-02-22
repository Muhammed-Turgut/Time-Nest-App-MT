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
fun GoalsScreen(item: List<Item>, saveFunction: (Item) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(item) { currentItem ->
                GoalsROW(item = currentItem)
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

        if (showDialog) {
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
fun CustomDialog(onDismiss: () -> Unit, saveFunction: (Item) -> Unit) {
    var itemName by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

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
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Başlama Tarihi") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("Bitiş Tarihi") },
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
                            if (itemName.isNotBlank()) {
                                saveFunction(
                                    Item(
                                        missionName = itemName,
                                        startTime = startTime,
                                        endTime = endTime
                                    )
                                )
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