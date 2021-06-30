package com.mertaydin.rickandmortymvvm.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mertaydin.rickandmortymvvm.viewmodel.EpisodeViewModel

class EpisodeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EpisodeViewModel::class.java))
            return EpisodeViewModel() as T

        throw IllegalArgumentException("UnknownViewModel")
    }

}