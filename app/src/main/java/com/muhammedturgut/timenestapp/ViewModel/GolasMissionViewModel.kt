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

class GolasMissionViewModel(application: Application): AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        getApplication(),
        ItemGoalDatabase::class.java, 
        "Items"
    )
    .addMigrations(ItemGoalDatabase.MIGRATION_1_2)
    .build()

    private val itemGoalDao = db.itemGoalDao()
    private val _itemList = MutableStateFlow<List<Item>>(emptyList())
    private val _itemListAim = MutableStateFlow<List<Item>>(emptyList())
    val itemListAim:StateFlow<List<Item>> = _itemListAim
    val itemList: StateFlow<List<Item>> = _itemList
    val selectedItem = mutableStateOf<Item>(Item("","","",1))


    init {
        getItemList()
    }

    fun getItemList() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = itemGoalDao.GolasScreenData()
            _itemList.value = items

            val filteredItems = items.filter { it.State != 1 }
            _itemList.value = filteredItems

        }
    }

    fun saveItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.insert(item)
            val updatedItems = itemGoalDao.GolasScreenData()
            _itemList.value = updatedItems
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.delete(item)
            val updatedItems = itemGoalDao.GolasScreenData()
            _itemList.value = updatedItems
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.update(item)
            val updatedItems = itemGoalDao.GolasScreenData()
            _itemList.value = updatedItems
        }
    }



    fun getItemListAim() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = itemGoalDao.AimScreenData()
            _itemListAim.value = items

            val filteredItems = items.filter { it.State != 1 }
            _itemListAim.value = filteredItems

        }
    }

    fun saveItemAim(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.insert(item)
            val updatedItems = itemGoalDao.AimScreenData()
            _itemListAim.value = updatedItems
        }
    }

    fun deleteItemAaim(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.delete(item)
            val updatedItems = itemGoalDao.AimScreenData()
            _itemListAim.value = updatedItems
        }
    }

    fun updateItemAim(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.update(item)
            val updatedItems = itemGoalDao.AimScreenData()
            _itemListAim.value = updatedItems
        }
    }
}