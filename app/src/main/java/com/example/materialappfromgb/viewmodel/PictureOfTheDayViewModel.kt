package com.example.materialappfromgb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.materialappfromgb.BuildConfig
import com.example.materialappfromgb.model.PictureOfTheDayResponseData
import com.example.materialappfromgb.model.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(private val liveData:MutableLiveData<AppState>, val repositoryImpl: RepositoryImpl = RepositoryImpl()):ViewModel() {

    fun getLiveData():MutableLiveData<AppState> {
        return liveData
    }

    fun sendRequest() {
        liveData.postValue(AppState.Loading)
        repositoryImpl.getPictureOfTheDayAPI().getPictureOfTheDay(BuildConfig.NASA_API_KEY)
            .enqueue(callback)
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if(response.isSuccessful){
                liveData.postValue(AppState.Success(response.body()!!))
            }else {
                liveData.postValue(AppState.Error(throw IllegalStateException("error :((")))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            TODO("Not yet implemented")
        }

    }
}