package com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemDetailChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB.ItemChainDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class ChainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        getApplication(),
        ItemChainDatabase::class.java,
        "chain_database"
    ).build()

    private val itemChainDao = db.itemChainDao()
    private val itemDetailDao = db.itemDetailDao()


    private val _itemList = MutableStateFlow<List<ItemChain>>(emptyList())
    val itemListChain: StateFlow<List<ItemChain>> = _itemList
    val selectedItem = mutableStateOf(ItemChain("", "", 1,1))

    private val _itemChainDaoDetaisl = MutableStateFlow<List<ItemDetailChain>>(emptyList())
    val itemListDeatilsChain: StateFlow<List<ItemDetailChain>> = _itemChainDaoDetaisl




    init {
        getItemList()
        getItemListDetails()
    }

    fun getItemList(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items=itemChainDao.getItemWithNameAndId()
                _itemList.value=items

            }catch (e:Exception){
                println("Hata: ${e.message}")
            }
        }
    }

    fun getItemListDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = itemDetailDao.getDetailsByNotId(selectedItem.value.notId)
                _itemChainDaoDetaisl.value = items

            }catch (e:Exception){
                println("Hata: ${e.message}")
            }
        }
    }

    fun getItemNot(notId:Int){
        viewModelScope.launch (Dispatchers.IO){
            val items=itemChainDao.getItemBynOTId(notId)
            items?.let {
                selectedItem.value=items
            }
        }
    }

    fun saveItem(item: ItemChain){
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.insert(item)
            val updateItems = itemChainDao.getItemWithNameAndId()
            _itemList.value=updateItems

        }
    }

    fun deleteItem(item: ItemChain){
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.delete(item)
            val updateItem=itemChainDao.getItemWithNameAndId()
            _itemList.value=updateItem
        }
    }

    fun updateItem(item: ItemChain){
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.update(item)
            val updateItems=itemChainDao.getItemWithNameAndId()
            _itemList.value=updateItems
        }
    }


}