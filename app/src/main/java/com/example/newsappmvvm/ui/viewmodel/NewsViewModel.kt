package com.example.newsappmvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappmvvm.model.domen.Article
import com.example.newsappmvvm.model.domen.News
import com.example.newsappmvvm.model.repository.Repository
import com.example.newsappmvvm.utils.NetworkStatus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository) : ViewModel() {

    private val _newsBreaking = MutableLiveData<NetworkStatus<News>>()
    val newsBreaking: LiveData<NetworkStatus<News>> get() = _newsBreaking

    private val _newsQuery = MutableLiveData<NetworkStatus<News>>()
    val newsQuery: LiveData<NetworkStatus<News>> get() = _newsQuery

    val getAllArticle: LiveData<List<Article>> = repository.fetchArticles()
    val connection = MutableLiveData<Boolean>()

    fun getBreakingNews(category: String) {
        viewModelScope.launch {
            repository.getBreakingNews(category = category).collect { response ->
                _newsBreaking.postValue(response)
            }
        }
    }

    fun getSearchingQuery(query: String) {
        viewModelScope.launch {
            repository.getSearchingQuery(query).collect { response ->
                _newsQuery.postValue(response)
            }
        }
    }

    fun insert(article: Article) {
        viewModelScope.launch {
            repository.insert(article)
        }
    }

    fun deleteOneItem(article: Article) {
        viewModelScope.launch {
            repository.deleteOneItem(article)
        }
    }

    fun deleteAllItem() {
        viewModelScope.launch {
            repository.deleteAllItem()
        }
    }

    fun existsItem(url:String) = repository.existsItem(url)
}