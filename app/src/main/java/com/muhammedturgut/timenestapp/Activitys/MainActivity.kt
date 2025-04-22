package com.muhammedturgut.timenestapp.Activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.ChainViewModel
import com.muhammedturgut.timenestapp.TimerScreen.ViewModel.TimerViewModel
import com.muhammedturgut.timenestapp.ToDo.Screens.ViewModel.GolasMissionViewModel
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme

class MainActivity : ComponentActivity() {
    private val viewModelToDo: GolasMissionViewModel by viewModels()
    private val viewModelTimer: TimerViewModel by viewModels()
    private val viewModelChain: ChainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TimeNestAppTheme {

                MainPage(viewModelToDo,viewModelChain,viewModelTimer)

            }
        }
    }
}
