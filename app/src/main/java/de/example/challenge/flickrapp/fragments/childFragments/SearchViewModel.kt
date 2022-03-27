package de.example.challenge.flickrapp.fragments.childFragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.flickrapi.FlickrApi
import de.example.challenge.flickrapp.flickrapi.models.PhotoModel
import de.example.challenge.flickrapp.flickrapi.models.PhotosSearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private var photosLiveData: MutableLiveData<List<PhotoModel>>? = null
    private var photoSearchModelLiveData: MutableLiveData<PhotosSearchModel> = MutableLiveData()
    private val flickrApiService = FlickrApi.createForSearch()

    fun getPhotosLiveData(): LiveData<List<PhotoModel>> {
        if (photosLiveData == null) {
            photosLiveData = MutableLiveData()
            photosLiveData?.postValue(listOf<PhotoModel>())
        }
        return photosLiveData!!
    }

    fun searchFor(requestText: String) {
        searchPhotos(requestText = requestText)
    }

    fun loadMore() {
        //TODO: implement loading next pages for endless scrolling
    }

    private fun searchPhotos(page: Int = 1, requestText: String) {
        Log.d("TAG", "Starting searching")
        flickrApiService.searchPhoto(page = page, text = requestText, apiKey = App.API_KEY).apply {
            enqueue(object : Callback<PhotosSearchModel> {
                override fun onResponse(
                    call: Call<PhotosSearchModel>,
                    response: Response<PhotosSearchModel>
                ) {
                    Log.d("TAG", "is successful: " + response.isSuccessful)
                    if (response.isSuccessful) {
                        photoSearchModelLiveData.postValue(response.body())
                        //TODO add behavior for empty list
                        photosLiveData?.postValue(response.body()?.photos?.photo)
                    } else {
                        //TODO implement error management
                    }
                }

                override fun onFailure(p0: Call<PhotosSearchModel>, p1: Throwable) {
                    Log.d("TAG", "onFailure: " + p1.cause)
                }
            })
        }
    }
}