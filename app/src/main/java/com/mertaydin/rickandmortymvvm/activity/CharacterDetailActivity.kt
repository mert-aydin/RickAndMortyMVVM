package com.mertaydin.rickandmortymvvm.activity

import android.os.Bundle
import android.view.View
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
    private var isCollapsed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        character = intent.getParcelableExtra(CHARACTER_KEY)!!

        close_button.setOnClickListener { finish() }

        initViews()

        val viewModel = ViewModelProvider(this, EpisodeViewModelFactory()).get(EpisodeViewModel::class.java)

        arrow.setOnClickListener {
            if (viewModel.list.value.isNullOrEmpty()) {
                progress_bar.visibility = View.VISIBLE
                character.episode?.forEach {
                    viewModel.loadEpisodes(this@CharacterDetailActivity, it)
                }

                viewModel.list.observe(this@CharacterDetailActivity, {
                    if (episodes_recycler_view.adapter == null)
                        episodes_recycler_view.adapter = EpisodeRecyclerViewAdapter(it)
                    else
                        episodes_recycler_view.adapter!!.notifyItemRangeInserted(viewModel.list.value!!.size, 1)

                    if (it.size == character.episode!!.size)
                        progress_bar.visibility = View.GONE
                })
            }

            isCollapsed = !isCollapsed
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
        updateLayoutParams { height = if (isCollapsed) 1 else 0 }

        arrow.animate().rotation(if (isCollapsed) 180F else 0F).start()
    }
}