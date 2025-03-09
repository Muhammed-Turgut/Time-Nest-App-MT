package com.muhammedturgut.timenestapp.TimerScreen.ViewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem
import com.muhammedturgut.timenestapp.TimerScreen.RoomDB.TimerItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TimerViewModel (application: Application):AndroidViewModel(application){

    private val db = Room.databaseBuilder(
        getApplication(),
        TimerItemDatabase::class.java,
        "timer_item"
    ).build()

    private val itemTimerDao = db.TimerItemDao()
    private val _timerItemList= MutableStateFlow<List<TimerItem>>(emptyList())
    val itemTimerList: StateFlow<List<TimerItem>> =_timerItemList
    val selectedTimerItem = mutableStateOf<TimerItem>(TimerItem("",0,0,0,0))


    init {
        getItemList()
    }

    fun getItemList(){
        viewModelScope.launch(Dispatchers.IO){

            try {
                val items=itemTimerDao.getItemWithNameAndId()
                _timerItemList.value=items
            }catch (e:Exception){
                println("Fails: ${e.message}")
            }
        }
    }

    fun saveItem(item:TimerItem){
        viewModelScope.launch(Dispatchers.IO) {
            itemTimerDao.insert(item)
            val updateItems=itemTimerDao.getItemWithNameAndId()
            _timerItemList.value=updateItems
        }
    }

    fun deleteItem(item:TimerItem){
        viewModelScope.launch(Dispatchers.IO) {
            itemTimerDao.delete(item)
            val updateItems=itemTimerDao.getItemWithNameAndId()
            _timerItemList.value=updateItems
        }
    }

    fun updateItem(item: TimerItem){
        viewModelScope.launch(Dispatchers.IO) {
            itemTimerDao.update(item)
            val updatedItmes = itemTimerDao.getItemWithNameAndId()
            _timerItemList.value=updatedItmes
        }
    }

}