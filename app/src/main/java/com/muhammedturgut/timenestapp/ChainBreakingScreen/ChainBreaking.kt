package com.muhammedturgut.timenestapp.ChainBreakingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor

@Composable
fun ChainBreakingScreen(items: List<ItemChain>,
                        saveFunction: (ItemChain) -> Unit,
                        delete: (ItemChain) -> Unit,
                        navHostController: NavHostController) {

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
                    ChainBreakingRow(item, delete = {
                        delete(item) },
                        navHostController=navHostController,)
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
fun ChainBreakingRow(
    item: ItemChain,
    delete: (ItemChain) -> Unit,
    navHostController: NavHostController

) {
    val backgroundColor = Color.White
    val textColor = Color.Black
    val isChainContinuing = item.chainState == 1

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null // Ripple efektini kaldırır
            ) {
                navHostController.navigate("DetailsChanScreen/${item.notId}")
            }
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.chainbreakingicon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.chainName ?: "Chain Name",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                color = textColor
            )
        }
        Text(
            text = item.chainAbout ?: "Chain About",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            color = textColor,
            fontSize = 14.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isChainContinuing) "Devam ediyor" else "Zincir Kırıldı",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = if (isChainContinuing) Color.Green else Color.Red,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(R.drawable.trashchanrow),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { delete(item) }
            )
        }
    }
}

@Composable
fun CustomDialogChain(onDismiss: () -> Unit, saveFunction: (ItemChain) -> Unit) {
    var itemChainName by remember { mutableStateOf("") }
    var itemChainAbout by remember { mutableStateOf("") }
    var chainGoal by remember { mutableStateOf("") }
    var chainStartDate by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 4.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Yeni Görev Ekle",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryColor
                )

                OutlinedTextField(
                    value = itemChainName,
                    onValueChange = { itemChainName = it },
                    label = { Text("Zincir Adı") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = itemChainAbout,
                    onValueChange = { itemChainAbout = it },
                    label = { Text("Zincir Açıklaması") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = chainGoal,
                    onValueChange = { chainGoal = it },
                    label = { Text("Hedef (örn: 30 gün)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                /*OutlinedTextField(
                    value = chainStartDate,
                    onValueChange = { chainStartDate = it },
                    label = { Text("Başlangıç Tarihi (örn: 21.04.2025)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )*/ //Bunu otomotik olarak kendim alacağım.

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
                    ) {
                        Text("İptal")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (itemChainName.isNotBlank()) {
                                // Burada chainGoal ve chainStartDate henüz ItemChain modeline eklenmediği için dahil edilmedi
                                saveFunction(ItemChain(chainName = itemChainName, chainAbout = itemChainAbout, 1, 0))
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f),
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