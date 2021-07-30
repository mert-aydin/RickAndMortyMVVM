package com.mertaydin.rickandmortymvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mertaydin.rickandmortymvvm.databinding.CharacterRecyclerViewItemBinding
import com.mertaydin.rickandmortymvvm.model.CharacterModel

class CharacterRecyclerViewAdapter(private val dataSet: ArrayList<CharacterModel>) :
    RecyclerView.Adapter<CharacterRecyclerViewAdapter.CharacterViewHolder>() {

    var onItemClick: ((CharacterModel) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = CharacterViewHolder(
        CharacterRecyclerViewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    )

    override fun onBindViewHolder(viewHolder: CharacterViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class CharacterViewHolder(private val binding: CharacterRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterModel: CharacterModel) {
            binding.name.text = characterModel.name
            Glide.with(binding.avatar.context).load(characterModel.image).into(binding.avatar)
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(dataSet[adapterPosition])
            }
        }
    }
}



