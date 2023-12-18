package com.mertaydin.rickandmortymvvm.viewmodel

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.mertaydin.rickandmortymvvm.R
import com.mertaydin.rickandmortymvvm.model.CharacterModelHolder
import com.mertaydin.rickandmortymvvm.model.CharacterModel
import com.mertaydin.rickandmortymvvm.util.Constants.Companion.API_URL

class CharacterViewModel : ViewModel() {

    var next: String? = null
    var list = MutableLiveData<ArrayList<CharacterModel>>()
    var shouldFinish = MutableLiveData<Boolean>()

    fun loadCharacters(context: Context, url: String = API_URL) {
        Volley.newRequestQueue(context).add(StringRequest(Request.Method.GET, url, {
            val json: CharacterModelHolder = Gson().fromJson(it, CharacterModelHolder::class.java)
            next = json.info?.next
            val temp = list.value ?: arrayListOf()
            json.results?.forEach {
                temp.add(it)
            }
            list.value = temp
        }, {
            AlertDialog.Builder(context).apply {
                setTitle(context.getString(R.string.character_fetch_failed))
                setMessage(it.localizedMessage)
                setPositiveButton(context.getString(R.string.retry)) { _, _ ->
                    loadCharacters(context, url)
                }
                setNegativeButton(context.getString(R.string.close)) { _, _ ->
                    shouldFinish.value = true
                }
                show()
            }
        }))
    }

}
