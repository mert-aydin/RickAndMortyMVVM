package com.mertaydin.rickandmortymvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java))
            return CharacterViewModel() as T

        throw IllegalArgumentException("UnknownViewModel")
    }

}