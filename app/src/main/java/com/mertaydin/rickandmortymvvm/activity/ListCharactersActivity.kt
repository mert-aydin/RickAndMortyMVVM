package com.mertaydin.rickandmortymvvm.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mertaydin.rickandmortymvvm.adapter.CharacterRecyclerViewAdapter
import com.mertaydin.rickandmortymvvm.databinding.ActivityListCharactersBinding
import com.mertaydin.rickandmortymvvm.util.CharacterViewModelFactory
import com.mertaydin.rickandmortymvvm.util.Constants.Companion.CHARACTER_KEY
import com.mertaydin.rickandmortymvvm.util.Constants.Companion.ITEM_PER_PAGE
import com.mertaydin.rickandmortymvvm.viewmodel.CharacterViewModel

class ListCharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel =
            ViewModelProvider(this, CharacterViewModelFactory())[CharacterViewModel::class.java]
                .apply {
                    loadCharacters(this@ListCharactersActivity)

                    list.observe(this@ListCharactersActivity) {
                        binding.characterRecyclerView.adapter?.notifyItemRangeInserted(
                            list.value!!.size,
                            ITEM_PER_PAGE
                        ) ?: run {
                            binding.characterRecyclerView.adapter =
                                CharacterRecyclerViewAdapter(it).apply {
                                    onItemClick = {
                                        startActivity(
                                            Intent(
                                                this@ListCharactersActivity,
                                                CharacterDetailActivity::class.java
                                            ).putExtra(CHARACTER_KEY, it)
                                        )
                                    }
                                }
                        }

                        binding.progressBar.visibility = View.GONE
                    }

                    shouldFinish.observe(this@ListCharactersActivity) {
                        if (it) finish()
                    }
                }

        binding.characterRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.next?.let {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.layout.bringChildToFront(binding.progressBar)
                        viewModel.loadCharacters(recyclerView.context, it)
                    }
                }
            }
        })
    }

}