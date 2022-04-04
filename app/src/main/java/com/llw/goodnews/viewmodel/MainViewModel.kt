package com.llw.goodnews.viewmodel

import androidx.lifecycle.*
import com.llw.goodnews.repository.EpidemicNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * MainViewModel
 * @author llw
 * @date 2022/3/29 23:54
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    repository: EpidemicNewsRepository
) : ViewModel() {

    val result = repository.getEpidemicNews()

}