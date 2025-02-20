package com.muhammedturgut.timenestapp.ViewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.muhammedturgut.timenestapp.ModelClass.Item
import com.muhammedturgut.timenestapp.RoomDB.ItemGoalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GolasMissionViewModel(application: Application): AndroidViewModel(application){

    private val db=Room.databaseBuilder(
        getApplication(),
        ItemGoalDatabase::class.java,"Items"
    ).build()

    private val itemGoalDao = db.itemGoalDao()
    val itemList = mutableStateOf<List<Item>>(listOf())
    val selectedItem = mutableStateOf<Item>(Item("","",""))

    init{
        getItemList()
    }
init {
    getItemList()
}
    fun getItemList(){
        viewModelScope.launch (Dispatchers.IO){
            itemList.value=itemGoalDao.getItemWithNameAndId()
        }
    }

    fun getItem(id:Int){
        viewModelScope.launch (Dispatchers.IO){
            val item=itemGoalDao.getItemById(id)
            item?.let {
                selectedItem.value = it
            }
        }
    }

    fun saveItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.insert(item)
        }
    }
}