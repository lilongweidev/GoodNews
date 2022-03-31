package com.llw.goodnews.viewmodel

import androidx.lifecycle.*
import com.llw.goodnews.bean.EpidemicNews
import com.llw.goodnews.repository.EpidemicNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @description MainViewModel
 * @author llw
 * @date 2022/3/29 9:54
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    repository: EpidemicNewsRepository
) : ViewModel() {

    val result = repository.getEpidemicNews()
}