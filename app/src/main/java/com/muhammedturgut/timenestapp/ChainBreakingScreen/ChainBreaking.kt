package com.muhammedturgut.timenestapp.ChainBreakingScreen

import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor
import java.util.Calendar

@Composable
fun ChainBreakingScreen(items: List<ItemChain>, saveFunction: (ItemChain) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

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
                text = "Alışkanlıklarım",
                fontFamily = FontFamily(Font(R.font.righteousregular)),
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )

            LazyColumn {
                items(items) { item ->
                    ChainBreakingRow(item)
                }
            }

            //Fab bölümü
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FABChain {
                   showDialog=true
                }
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = showDialog,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomDialogChain(
                    onDismiss = { showDialog = false },
                    saveFunction = { newItem ->
                        saveFunction(newItem)
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun FABChain(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFF808080)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(34.dp))
    }
}

@Composable
fun ChainBreakingRow(item: ItemChain) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(16.dp)
            .shadow(
                elevation = 8.dp,
                ambientColor = Color.Black,
                spotColor = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color(0xFFF7F7F7)),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(R.drawable.chainbreakingicon), contentDescription = null)
            Text(
                text = item.chainName ?: "Chain Name",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Image(painter = painterResource(R.drawable.rowdelete), contentDescription = null)
        }
        Text(
            text = item.chainAbout ?: "Chain About",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if(item.chainState == 1) "Devam ediyor" else "Zincir Kırıldı",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
            Image(
                painter = painterResource(R.drawable.trashchanrow),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 12.dp, end = 16.dp)
                    .size(38.dp)
            )
        }
    }
}

@Composable
fun CustomDialogChain(onDismiss: () -> Unit, saveFunction: (ItemChain) -> Unit) {
    var itemChainName by remember { mutableStateOf("") }
    var itemChainAbout by remember { mutableStateOf("") }


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
                    value = itemChainName,
                    onValueChange = { itemChainName = it },
                    label = { Text("Görev Adı") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = itemChainAbout,
                    onValueChange = { itemChainAbout = it },
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
                            if (itemChainName.isNotBlank()) {
                                saveFunction(ItemChain(chainName=itemChainName, chainAbout = itemChainAbout, 1))
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
fun GreetingPreview() {

}