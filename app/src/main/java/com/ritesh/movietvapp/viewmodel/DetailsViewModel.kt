package com.ritesh.movietvapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.movietvapp.model.MovieDetails
import com.ritesh.movietvapp.repository.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val mediaRepository: MediaRepository, private val apiKey: String):ViewModel() {
    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isExpanded = MutableLiveData<Boolean>()
    val isExpanded: LiveData<Boolean> = _isExpanded

    fun getMovieDetails(titleId: Int): LiveData<MovieDetails> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val details = mediaRepository.getMediaDetails(titleId, apiKey)
                _movieDetails.postValue(details)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _errorMessage.postValue(e.localizedMessage)
            }
        }
        return movieDetails
    }

    fun toggleExpanded() {
        _isExpanded.value = !(_isExpanded.value ?: false)
    }

}