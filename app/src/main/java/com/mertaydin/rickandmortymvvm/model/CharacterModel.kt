package com.mertaydin.rickandmortymvvm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterModel(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val gender: String?,
    val image: String?,
    val episode: ArrayList<String>?
) : Parcelable