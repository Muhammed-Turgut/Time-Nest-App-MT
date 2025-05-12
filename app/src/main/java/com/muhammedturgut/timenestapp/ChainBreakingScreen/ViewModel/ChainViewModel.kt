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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale


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

    val selectedItem = mutableStateOf(ItemChain(chainName = "", chainAbout =  "", chainState = 1, notId = 0))

    private val _itemChainDaoDetails = MutableStateFlow<List<ItemDetailChain>>(emptyList())
    val itemListDetailsChain: StateFlow<List<ItemDetailChain>> = _itemChainDaoDetails




    init {
        getItemList()
        getItemListDetails(1)
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

    fun getItemListDetails(notId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = itemDetailDao.getDetailsByNotId(notId)
                _itemChainDaoDetails.value = items

            }catch (e:Exception){
                println("Hata: ${e.message}")
            }
        }
    }

    suspend fun getItemNot(notId: Int): ItemChain? {
        return itemChainDao.getItemByNOTId(notId)
    }

    fun saveItem(item: ItemChain){
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.insert(item)
            val updateItems = itemChainDao.getItemWithNameAndId()
            _itemList.value=updateItems

        }
    }

    fun saveItemDetail(item: ItemDetailChain){
        viewModelScope.launch(Dispatchers.IO){
            itemDetailDao.insert(item)
            val updateItem = itemDetailDao.getDetailsByNotId(item.notOwnerId)
            _itemChainDaoDetails.value=updateItem
        }
    }

    fun deleteItem(item: ItemChain){
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.delete(item)
            val updateItem=itemChainDao.getItemWithNameAndId()
            _itemList.value=updateItem
        }
    }
    fun deleteItemDetail(item: ItemDetailChain,notId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            itemDetailDao.delete(item)
            val updateItem = itemDetailDao.getDetailsByNotId(notId)
            _itemChainDaoDetails.value = updateItem
        }
    }

    fun updateItem(item: ItemChain){
        viewModelScope.launch(Dispatchers.IO) {
            itemChainDao.update(item)
            val updateItems=itemChainDao.getItemWithNameAndId()
            _itemList.value=updateItems
        }
    }

    fun nowMonth():Int{ //Güncel Ayın indekisin veriyor.
        val today = LocalDate.now()
        val currentMonthName = today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val currentMonthIndex = today.monthValue - 1
        return currentMonthIndex
    }

    fun getYearWithDays(): DateTime {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dayOfWeekFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val now = Calendar.getInstance()
        val monthsWithDays = mutableListOf<MonthWithDays>()

        val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(now.time)

        for (monthIndex in 0 until 12) {
            val currentDate = Calendar.getInstance() // her döngüde sıfırla
            currentDate.set(Calendar.YEAR, now.get(Calendar.YEAR))
            currentDate.set(Calendar.MONTH, monthIndex)
            currentDate.set(Calendar.DAY_OF_MONTH, 1)

            val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(currentDate.time)
            val yearName = SimpleDateFormat("yyyy", Locale.getDefault()).format(currentDate.time)
            val daysInMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH)

            val days = mutableListOf<DayInfo>()
            for (day in 1..daysInMonth) {
                currentDate.set(Calendar.DAY_OF_MONTH, day)
                val dayOfWeek = dayOfWeekFormat.format(currentDate.time)
                days.add(DayInfo(dayOfWeek = dayOfWeek, dayNumber = day))
            }

            monthsWithDays.add(MonthWithDays(monthName = monthName, days = days))
        }

        return DateTime(year = currentYear, monthName = monthsWithDays)
    }

    fun getMonthData(monthIndex: Int): MonthWithDays {
        val allMonths = getYearWithDays()
        return allMonths.monthName[monthIndex] // 0 = Ocak, 1 = Şubat, ...
    }

}

data class DateTime(
    val year: String,
    val monthName: List<MonthWithDays>
)

data class MonthWithDays(
    val monthName: String,
    val days: List<DayInfo>
)

data class DayInfo(
    val dayOfWeek: String,  // Örneğin: "Pzt"
    val dayNumber: Int      // Örneğin: 5
)
