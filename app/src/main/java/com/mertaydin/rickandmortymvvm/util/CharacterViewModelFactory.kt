package com.mertaydin.rickandmortymvvm.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mertaydin.rickandmortymvvm.viewmodel.CharacterViewModel

class CharacterViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CharacterViewModel::class.java))
            CharacterViewModel() as T
        else
            throw IllegalArgumentException("UnknownViewModel")
    }

}