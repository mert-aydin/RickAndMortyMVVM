package com.mertaydin.rickandmortymvvm

data class CharacterModelHolder(var info: InfoModel?,
                                var results: ArrayList<CharacterModel>?)

data class InfoModel(var next: String?)

data class CharacterModel(var id: Int?,
                          var name: String?,
                          var status: String?,
                          var species: String?,
                          var gender: String?,
                          var image: String?,
                          var episode: ArrayList<String>?)