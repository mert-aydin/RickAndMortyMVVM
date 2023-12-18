package com.mertaydin.rickandmortymvvm.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.mertaydin.rickandmortymvvm.R
import com.mertaydin.rickandmortymvvm.adapter.EpisodeRecyclerViewAdapter
import com.mertaydin.rickandmortymvvm.databinding.ActivityCharacterDetailBinding
import com.mertaydin.rickandmortymvvm.model.CharacterModel
import com.mertaydin.rickandmortymvvm.util.Constants.Companion.CHARACTER_KEY
import com.mertaydin.rickandmortymvvm.util.EpisodeViewModelFactory
import com.mertaydin.rickandmortymvvm.viewmodel.EpisodeViewModel

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private lateinit var character: CharacterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(CHARACTER_KEY, CharacterModel::class.java)!!
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra(CHARACTER_KEY)!!
        }

        binding.closeButton.setOnClickListener { finish() }

        initViews()

        val viewModel =
            ViewModelProvider(this, EpisodeViewModelFactory())[EpisodeViewModel::class.java]

        binding.episodeTv.setOnClickListener {
            if (viewModel.list.value.isNullOrEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                character.episode?.forEach {
                    viewModel.loadEpisodes(this@CharacterDetailActivity, it)
                }

                viewModel.list.observe(this@CharacterDetailActivity) {
                    if (it.size == character.episode?.size) {
                        binding.progressBar.visibility = GONE
                        binding.episodesRecyclerView.adapter = EpisodeRecyclerViewAdapter(it)
                        binding.episodesRecyclerView.toggle()
                    }
                }
            } else binding.episodesRecyclerView.toggle()
        }
    }

    private fun initViews() {
        binding.characterName.text = character.name
        Glide.with(this).load(character.image).into(binding.avatar)
        binding.statusAndSpecies.text =
            getString(R.string.status_and_species, character.status, character.species)
        binding.characterGender.text = character.gender
    }

    private fun RecyclerView.toggle() {
        TransitionManager.beginDelayedTransition(this, null)
        binding.episodesRecyclerView.visibility = GONE - binding.episodesRecyclerView.visibility
        updateLayoutParams { height = 1 - height }
        binding.arrow.animate().rotation(180F - binding.arrow.rotation).start()
    }
}