package com.mertaydin.rickandmortymvvm.activity

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
import java.util.*

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var bindind: ActivityCharacterDetailBinding
    private lateinit var character: CharacterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        character = intent.getParcelableExtra(CHARACTER_KEY)!!

        bindind.closeButton.setOnClickListener { finish() }

        initViews()

        val viewModel =
            ViewModelProvider(this, EpisodeViewModelFactory()).get(EpisodeViewModel::class.java)

        bindind.episodeTv.setOnClickListener {
            if (viewModel.list.value.isNullOrEmpty()) {
                bindind.progressBar.visibility = View.VISIBLE
                character.episode?.forEach {
                    viewModel.loadEpisodes(this@CharacterDetailActivity, it)
                }

                viewModel.list.observe(this@CharacterDetailActivity, {
                    if (it.size == character.episode!!.size) {
                        bindind.progressBar.visibility = GONE
                        bindind.episodesRecyclerView.adapter = EpisodeRecyclerViewAdapter(it)
                        bindind.episodesRecyclerView.toggle()
                    }
                })
            } else
                bindind.episodesRecyclerView.toggle()
        }
    }

    private fun initViews() {
        bindind.characterName.text = character.name
        Glide.with(this).load(character.image).into(bindind.avatar)
        bindind.statusAndSpecies.text =
            getString(R.string.status_and_species, character.status, character.species)
        bindind.characterGender.text = character.gender
    }

    private fun RecyclerView.toggle() {
        TransitionManager.beginDelayedTransition(this, null)
        bindind.episodesRecyclerView.visibility = GONE - bindind.episodesRecyclerView.visibility
        updateLayoutParams { height = 1 - height }
        bindind.arrow.animate().rotation(180F - bindind.arrow.rotation).start()
    }
}