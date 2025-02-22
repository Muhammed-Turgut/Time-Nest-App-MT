package com.muhammedturgut.timenestapp.ViewModel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.muhammedturgut.timenestapp.ModelClass.Item
import com.muhammedturgut.timenestapp.RoomDB.ItemGoalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GolasMissionViewModel(application: Application): AndroidViewModel(application){

    private val db = Room.databaseBuilder(
        getApplication(),
        ItemGoalDatabase::class.java, 
        "Items"
    )
    .addMigrations(ItemGoalDatabase.MIGRATION_1_2)
    .build()

    private val itemGoalDao = db.itemGoalDao()
    private val _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList: StateFlow<List<Item>> = _itemList
    val selectedItem = mutableStateOf<Item>(Item("","",""))

    init{
        getItemList()
    }

    fun getItemList(){
        viewModelScope.launch (Dispatchers.IO){
            val items = itemGoalDao.getItemWithNameAndId()
            _itemList.value = items
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

    fun saveItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.insert(item)
            // Veri eklendikten sonra listeyi güncelle
            val updatedItems = itemGoalDao.getItemWithNameAndId()
            _itemList.value = updatedItems
        }
    }
}