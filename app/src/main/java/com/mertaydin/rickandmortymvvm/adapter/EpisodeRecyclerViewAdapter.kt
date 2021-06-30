package com.mertaydin.rickandmortymvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertaydin.rickandmortymvvm.R
import com.mertaydin.rickandmortymvvm.model.EpisodeModel
import kotlinx.android.synthetic.main.episode_recycler_view_item.view.*

class EpisodeRecyclerViewAdapter(private val dataSet: ArrayList<EpisodeModel>) :
    RecyclerView.Adapter<EpisodeRecyclerViewAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = EpisodeViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.episode_recycler_view_item, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: EpisodeViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class EpisodeViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(episodeModel: EpisodeModel) {
            binding.episode_name.text = binding.context.getString(
                R.string.episode_name,
                episodeModel.name,
                episodeModel.episode!!.substring(0, 3),
                episodeModel.episode.substring(3)
            )
        }
    }
}



