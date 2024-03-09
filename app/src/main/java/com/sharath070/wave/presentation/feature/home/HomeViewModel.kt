package com.sharath070.wave.presentation.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharath070.wave.common.Resource
import com.sharath070.wave.domain.useCase.homePageUseCase.GetHomePageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePageData: GetHomePageData
): ViewModel() {

    var uiStates by mutableStateOf(HomeUiStates())
        private set

    init {
        getData()
    }

    private fun getData() {
        uiStates = uiStates.copy(loading = true)
        viewModelScope.launch {
            getHomePageData("hindi").collect { result ->
                if (result is Resource.Success) {
                    uiStates = uiStates.copy(
                        success = true,
                        data = result.data ?: mapOf(),
                        loading = false
                    )
                } else if (result is Resource.Error) {
                    uiStates = uiStates.copy(
                        error = result.msg,
                        loading = false
                    )
                }
            }
        }
    }

}