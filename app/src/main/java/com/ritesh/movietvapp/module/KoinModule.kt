package com.ritesh.movietvapp.module

import HomeViewModel
import com.ritesh.movietvapp.constant.Constants
import com.ritesh.movietvapp.repository.MediaRepository
import com.ritesh.movietvapp.services.WatchmodeApiService
import com.ritesh.movietvapp.viewmodel.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Provide the WatchmodeApiService
    single<WatchmodeApiService> {

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(WatchmodeApiService::class.java)
    }

    // Provide the MediaRepository
    single { MediaRepository(get()) }
    // Provide the API key
    single { Constants.API_KEY }

    // Provide the HomeViewModel
    viewModel { HomeViewModel(get(), get()) }
    // Provide the DetailsViewModel
    viewModel { DetailsViewModel(get(), get()) }
}
