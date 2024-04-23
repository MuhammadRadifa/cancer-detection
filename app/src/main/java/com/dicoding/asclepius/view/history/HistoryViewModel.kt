package com.dicoding.asclepius.view.history

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.HistoryEntity
import com.dicoding.asclepius.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(
    application: Application = Application()
):ViewModel() {
    private val historyRepository = HistoryRepository(application)
    var historyList: MutableLiveData<List<HistoryEntity>> = MutableLiveData()

    init {
        historyList.value = getHistory()
    }

    fun addHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            historyRepository.addHistory(historyEntity)
        }
    }

    private fun getHistory(): List<HistoryEntity> = historyRepository.getHistory()


}