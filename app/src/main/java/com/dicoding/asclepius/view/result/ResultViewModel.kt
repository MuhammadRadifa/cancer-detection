package com.dicoding.asclepius.view.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.data.state.ResultState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ResultViewModel: ViewModel(){
    private val _news = MutableLiveData<ResultState<List<ArticlesItem>>>()
    val news = _news

    init {
        getNews()
    }

    private fun getNews(){
        viewModelScope.launch {
            try {
                _news.value = ResultState.Loading
                val response = ApiConfig.getApiService().getNews()
                if(response.status == "ok") {
                    _news.value = ResultState.Success(response.articles)
                }
            }catch (e: HttpException){
                _news.value = ResultState.Error(e.message())
            }
        }
    }

}