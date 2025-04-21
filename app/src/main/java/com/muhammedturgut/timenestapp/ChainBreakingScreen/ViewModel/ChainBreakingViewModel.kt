package com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB.ItemChainDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChainBreakingViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        getApplication(),
        ItemChainDatabase::class.java,
        "ChainItems"
    ).build()

    private val itemChainDao = db.itemChainDao()
    private val _chainList = MutableStateFlow<List<ItemChain>>(emptyList())
    val chainList: StateFlow<List<ItemChain>> = _chainList

    init {
        getChainList()
    }

    fun getChainList() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = itemChainDao.getItemWithNameAndId()
            _chainList.value = items
        }
    }

    fun saveChainItem(item: ItemChain) {
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.insert(item)
            getChainList()
        }
    }

    fun deleteChainItem(item: ItemChain) {
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.delete(item)
            getChainList()
        }
    }

    fun updateChainItem(item: ItemChain) {
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.update(item)
            getChainList()
        }
    }
} 