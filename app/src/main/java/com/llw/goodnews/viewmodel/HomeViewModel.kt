package com.llw.goodnews.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.llw.goodnews.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * HomeViewModel
 * @author llw
 */
@HiltViewModel
class HomeViewModel @Inject constructor(repository: HomeRepository) :ViewModel () {

    val result = repository.getSocialNews()

    val resultMilitary = repository.getMilitaryNews()

    val resultTechnology = repository.getTechnologyNews()

    val resultFinance = repository.getFinanceNews()

    val resultAmusement = repository.getAmusementNews()

    val resultCollectionNews = repository.getCollectionNews()
}