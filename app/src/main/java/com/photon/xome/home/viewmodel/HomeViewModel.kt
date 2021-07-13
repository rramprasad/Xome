package com.photon.xome.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photon.xome.home.data.FlickrImage
import com.photon.xome.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _flickrImagesListLiveData = MutableLiveData<List<FlickrImage>>()
    val flickrImagesListLiveData : LiveData<List<FlickrImage>> = _flickrImagesListLiveData

    fun getFlickrImages(searchKeyword:String){
        viewModelScope.launch {
            homeRepository.getFlickrImages(searchKeyword).collect {
                _flickrImagesListLiveData.value = it
            }
        }
    }
}