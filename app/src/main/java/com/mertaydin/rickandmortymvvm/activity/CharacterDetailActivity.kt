package com.mertaydin.rickandmortymvvm.activity

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mertaydin.rickandmortymvvm.R
import com.mertaydin.rickandmortymvvm.adapter.EpisodeRecyclerViewAdapter
import com.mertaydin.rickandmortymvvm.model.CharacterModel
import com.mertaydin.rickandmortymvvm.util.EpisodeViewModelFactory
import com.mertaydin.rickandmortymvvm.viewmodel.EpisodeViewModel
import kotlinx.android.synthetic.main.activity_character_detail.*
import kotlinx.android.synthetic.main.activity_list_characters.*

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var character: CharacterModel
    private var isCollapsed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        character = intent.getParcelableExtra("character")!!

        close_button.setOnClickListener { finish() }

        initViews()

        ViewModelProvider(this, EpisodeViewModelFactory()).get(EpisodeViewModel::class.java).apply {
            character.episode?.forEach {
                loadEpisodes(this@CharacterDetailActivity, it)
            }

            list.observe(this@CharacterDetailActivity, {
                episodes_recycler_view.adapter = EpisodeRecyclerViewAdapter(it)
            })
        }

        arrow.setOnClickListener {
            isCollapsed = !isCollapsed
            if (isCollapsed) {
                episodes_recycler_view.updateLayoutParams { height = 1 }
                it.animate().rotation(0F).start()
            } else {
                episodes_recycler_view.updateLayoutParams { height = WRAP_CONTENT }
                it.animate().rotation(180F).start()
            }
        }
    }

    private fun initViews() {
        character_name.text = character.name
        Glide.with(this).load(character.image).into(avatar)
        status_and_species.text =
            getString(R.string.status_and_species, character.status, character.species)
        character_gender.text = character.gender
    }
}