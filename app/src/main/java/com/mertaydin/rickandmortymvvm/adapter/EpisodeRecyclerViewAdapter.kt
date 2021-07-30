package com.mertaydin.rickandmortymvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertaydin.rickandmortymvvm.R
import com.mertaydin.rickandmortymvvm.databinding.EpisodeRecyclerViewItemBinding
import com.mertaydin.rickandmortymvvm.model.EpisodeModel

class EpisodeRecyclerViewAdapter(private val dataSet: ArrayList<EpisodeModel>) :
    RecyclerView.Adapter<EpisodeRecyclerViewAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = EpisodeViewHolder(
        EpisodeRecyclerViewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    )

    override fun onBindViewHolder(viewHolder: EpisodeViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class EpisodeViewHolder(private val binding: EpisodeRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episodeModel: EpisodeModel) {
            binding.episodeName.text = binding.episodeName.context.getString(
                R.string.episode_name,
                episodeModel.name,
                episodeModel.episode!!.substring(0, 3),
                episodeModel.episode.substring(3)
            )
        }
    }
}



