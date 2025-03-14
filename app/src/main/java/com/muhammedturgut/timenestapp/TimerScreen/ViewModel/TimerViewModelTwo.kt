package com.muhammedturgut.timenestapp.TimerScreen.ViewModel

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem

//Bu ViewModel timeren arka planda geri saymaya devam ettmesini sağlıyor.

class TimerViewModelTwo() : ViewModel() {
    var remainingTime by mutableStateOf(0L)
        private set
    var remainingHour by mutableStateOf(0)
        private set
    var remainingMinute by mutableStateOf(0)
        private set
    var remainingSecond by mutableStateOf(0)
        private set
    var timerState by mutableStateOf(false)
        private set

    var timerStateFinish by mutableStateOf(false)
        private set

    private var timer: CountDownTimer? = null

    fun startTimer(totalTime: TimerItem,onTimerFinish: () -> Unit) {
        val totalMillis = totalTime.toLong()
        remainingTime = totalMillis
        remainingHour = totalTime.timer_hour
        remainingMinute = totalTime.timer_minute
        remainingSecond = totalTime.timer_second

        timer?.cancel()

        timer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                remainingHour = (millisUntilFinished / 3600000).toInt()
                remainingMinute = ((millisUntilFinished % 3600000) / 60000).toInt()
                remainingSecond = ((millisUntilFinished % 60000) / 1000).toInt()
            }

            override fun onFinish() {
                remainingTime = 0
                remainingHour = 0
                remainingMinute = 0
                remainingSecond = 0
                timerState = true
                timerStateFinish=true
                onTimerFinish()

            }
        }.apply { start() }

        timerState = true
    }

    fun stopTimer() {
        timer?.cancel()
        timerState = false
        timerStateFinish=false
    }
}