import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.movietvapp.model.Title
import com.ritesh.movietvapp.repository.MediaRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val mediaRepository: MediaRepository, private val apiKey: String) : ViewModel() {

    private val _movieTitles = MutableLiveData<List<Title>>(emptyList())
    val movieTitles: LiveData<List<Title>> = _movieTitles

    private val _tvShowTitles = MutableLiveData<List<Title>>(emptyList())
    val tvShowTitles: LiveData<List<Title>> = _tvShowTitles

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentType: String = "movie"

    init {
        fetchTitles() // Fetch initial data for both movies and TV shows
    }

    fun updateContentType(type: String) {
        if (currentType != type) {
            currentType = type
            fetchTitles() // Re-fetch data based on the selected type (movie or tv_show)
        }
    }

    private fun fetchTitles() {
        // If data is already fetched, skip fetching again
        if (_movieTitles.value?.isNotEmpty() == true && _tvShowTitles.value?.isNotEmpty() == true) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Make two simultaneous API calls
                Single.zip(
                    mediaRepository.getTitles(apiKey, "movie"),
                    mediaRepository.getTitles(apiKey, "tv_series"),
                    { movieResult, tvShowResult ->
                        movieResult to tvShowResult
                    }
                ).subscribe({ (movies, tvShows) ->
                    _movieTitles.postValue(movies)
                    _tvShowTitles.postValue(tvShows)
                    _isLoading.postValue(false)
                }, { error ->
                    _errorMessage.postValue(error.localizedMessage)
                })
            } catch (e: Exception) {
                _errorMessage.postValue(e.localizedMessage)
            }
        }
    }
}
