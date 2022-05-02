package com.llw.goodnews.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.llw.goodnews.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * HomeViewModel
 * @author llw
 * @date 2022/5/2 12:41
 */
@HiltViewModel
class HomeViewModel @Inject constructor(repository: HomeRepository) :ViewModel () {

    val result = repository.getSocialNews()
}