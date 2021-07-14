package com.photon.xome.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photon.xome.feature.home.data.FlickrImage
import com.photon.xome.feature.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _photosListState = MutableStateFlow<List<FlickrImage>>(emptyList())
    val photosListState : StateFlow<List<FlickrImage>> = _photosListState

    fun getFlickrImages(searchKeyword:String){
        viewModelScope.launch {
            homeRepository.getFlickrImages(searchKeyword).collect {
                _photosListState.value = it
            }
        }
    }
}