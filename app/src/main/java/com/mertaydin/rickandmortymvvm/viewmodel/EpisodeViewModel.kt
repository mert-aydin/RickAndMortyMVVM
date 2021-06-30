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
import com.mertaydin.rickandmortymvvm.model.EpisodeModel

class EpisodeViewModel : ViewModel() {

    var list = MutableLiveData<ArrayList<EpisodeModel>>()

    fun loadEpisodes(context: Context, url: String) {
        Volley.newRequestQueue(context).add(StringRequest(Request.Method.GET, url, {
            val episodeModel: EpisodeModel = Gson().fromJson(it, EpisodeModel::class.java)
            val temp = list.value ?: arrayListOf()
            temp.add(EpisodeModel(episodeModel.name,episodeModel.episode))
            list.value = temp
        }, {
            AlertDialog.Builder(context).apply {
                setTitle(context.getString(R.string.episode_fetch_failed))
                setMessage(it.localizedMessage)
                setPositiveButton(context.getString(R.string.retry)) { _, _ ->
                    loadEpisodes(context, url)
                }
                setNegativeButton(context.getString(android.R.string.ok)) { _, _ -> }
                show()
            }
        }))
    }

}
