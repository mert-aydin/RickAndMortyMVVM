package com.mertaydin.rickandmortymvvm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterModel(var id: Int?,
                          var name: String?,
                          var status: String?,
                          var species: String?,
                          var gender: String?,
                          var image: String?,
                          var episode: ArrayList<String>?) : Parcelable