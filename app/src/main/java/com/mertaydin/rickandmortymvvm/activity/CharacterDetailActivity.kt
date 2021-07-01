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
import com.mertaydin.rickandmortymvvm.model.CharacterModel
import com.mertaydin.rickandmortymvvm.util.Constants.Companion.CHARACTER_KEY
import com.mertaydin.rickandmortymvvm.util.EpisodeViewModelFactory
import com.mertaydin.rickandmortymvvm.viewmodel.EpisodeViewModel
import kotlinx.android.synthetic.main.activity_character_detail.*
import java.util.*

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var character: CharacterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        character = intent.getParcelableExtra(CHARACTER_KEY)!!

        close_button.setOnClickListener { finish() }

        initViews()

        val viewModel = ViewModelProvider(this, EpisodeViewModelFactory()).get(EpisodeViewModel::class.java)

        episode_tv.setOnClickListener {
            if (viewModel.list.value.isNullOrEmpty()) {
                progress_bar.visibility = View.VISIBLE
                character.episode?.forEach {
                    viewModel.loadEpisodes(this@CharacterDetailActivity, it)
                }

                viewModel.list.observe(this@CharacterDetailActivity, {
                    if (it.size == character.episode!!.size) {
                        progress_bar.visibility = GONE
                        episodes_recycler_view.adapter = EpisodeRecyclerViewAdapter(it)
                        episodes_recycler_view.toggle()
                    }
                })
            } else
                episodes_recycler_view.toggle()
        }
    }

    private fun initViews() {
        character_name.text = character.name
        Glide.with(this).load(character.image).into(avatar)
        status_and_species.text = getString(R.string.status_and_species, character.status, character.species)
        character_gender.text = character.gender
    }

    private fun RecyclerView.toggle() {
        TransitionManager.beginDelayedTransition(this, null)
        episodes_recycler_view.visibility = GONE - episodes_recycler_view.visibility
        updateLayoutParams { height = 1 - height }
        arrow.animate().rotation(180F - arrow.rotation).start()
    }
}