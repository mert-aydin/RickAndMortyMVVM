package com.mertaydin.rickandmortymvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mertaydin.rickandmortymvvm.R
import com.mertaydin.rickandmortymvvm.model.CharacterModel
import kotlinx.android.synthetic.main.character_recycler_view_item.view.*

class CharacterRecyclerViewAdapter(private val dataSet: ArrayList<CharacterModel>) : RecyclerView.Adapter<CharacterRecyclerViewAdapter.CharacterViewHolder>() {

    var onItemClick: ((CharacterModel) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = CharacterViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.character_recycler_view_item, viewGroup, false))

    override fun onBindViewHolder(viewHolder: CharacterViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class CharacterViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(characterModel: CharacterModel) {
            binding.name.text = characterModel.name
            Glide.with(binding.context).load(characterModel.image).into(binding.avatar)
        }

        init {
            binding.setOnClickListener {
                onItemClick?.invoke(dataSet[adapterPosition])
            }
        }
    }
}



