package com.mertaydin.rickandmortymvvm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class CharacterViewModel : ViewModel() {

    var next: String? = null
    var list = MutableLiveData<ArrayList<CharacterModel>>()

    fun loadCharacters(context: Context, url: String = "https://rickandmortyapi.com/api/character") {
        Volley.newRequestQueue(context).add(StringRequest(Request.Method.GET, url, {
            val json: CharacterModelHolder = Gson().fromJson(it, CharacterModelHolder::class.java)
            next = json.info!!.next
            val temp = list.value ?: arrayListOf()
            json.results!!.forEach {
                temp.add(it)
            }
            list.value = temp
        }, {

        }))
    }

}
