package com.muhammedturgut.timenestapp.ToDo.Screens.ViewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.ToDo.Screens.RoomDB.ItemGoalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GolasMissionViewModel(application: Application): AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        getApplication(),
        ItemGoalDatabase::class.java,
        "Items"
    ).build()

    private val itemGoalDao = db.itemGoalDao()
    private val _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList: StateFlow<List<Item>> = _itemList
    val selectedItem = mutableStateOf<Item>(Item("","","","",1))

    private val _itemListAim = MutableStateFlow<List<Item>>(emptyList())
    val itemListAim: StateFlow<List<Item>> = _itemListAim

    init {
        getItemList()
        getItemListAim()
    }

    fun getItemList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = itemGoalDao.getItemWithNameAndId()
                _itemList.value = items

            } catch (e: Exception) {
                println("Hata: ${e.message}")
            }
        }
    }

    fun getItemListAim() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = itemGoalDao.AimScreenData()
                _itemListAim.value = items
                println("Başarılar veri sayısı: ${items.size}")
            } catch (e: Exception) {
                println("Başarılar Hata: ${e.message}")
            }
        }
    }

    fun getItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = itemGoalDao.getItemById(id)
            item?.let {
                selectedItem.value = it
            }
        }
    }

    fun saveItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.insert(item)
            val updatedItems = itemGoalDao.getItemWithNameAndId()
            _itemList.value = updatedItems
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.delete(item)
            val updatedItems = itemGoalDao.getItemWithNameAndId()
            val updatedAimItems = itemGoalDao.AimScreenData()
            _itemList.value = updatedItems
            _itemListAim.value = updatedAimItems
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemGoalDao.update(item)
            val updatedItems = itemGoalDao.getItemWithNameAndId()
            val updatedAimItems = itemGoalDao.AimScreenData()
            _itemList.value = updatedItems
            _itemListAim.value = updatedAimItems
        }
    }
}