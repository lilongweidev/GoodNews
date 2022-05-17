package com.llw.goodnews.viewmodel

import androidx.lifecycle.*
import com.llw.goodnews.repository.EpidemicNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * MainViewModel
 * @author llw
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    repository: EpidemicNewsRepository
) : ViewModel() {

    private val isRefresh = MutableLiveData<Boolean>()

    // 当 isRefresh 值发生改变时，会执行 repository.getEpidemicNews(it)
    val result = Transformations.switchMap(isRefresh) {
        repository.getEpidemicNews(it)
    }

    fun getNews(refresh: Boolean = false) {
        isRefresh.value = refresh
    }

}