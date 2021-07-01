package com.mertaydin.rickandmortymvvm.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mertaydin.rickandmortymvvm.R
import com.mertaydin.rickandmortymvvm.adapter.CharacterRecyclerViewAdapter
import com.mertaydin.rickandmortymvvm.util.CharacterViewModelFactory
import com.mertaydin.rickandmortymvvm.util.Constants.Companion.CHARACTER_KEY
import com.mertaydin.rickandmortymvvm.util.Constants.Companion.ITEM_PER_PAGE
import com.mertaydin.rickandmortymvvm.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.activity_list_characters.*

class ListCharactersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_characters)

        val viewModel = ViewModelProvider(this, CharacterViewModelFactory()).get(CharacterViewModel::class.java).apply {
            loadCharacters(this@ListCharactersActivity)

            list.observe(this@ListCharactersActivity, {
                if (character_recycler_view.adapter == null)
                    character_recycler_view.adapter = CharacterRecyclerViewAdapter(it).apply {
                        onItemClick = {
                            startActivity(Intent(this@ListCharactersActivity, CharacterDetailActivity::class.java).putExtra(CHARACTER_KEY, it))
                        }
                    }
                else
                    character_recycler_view.adapter!!.notifyItemRangeInserted(list.value!!.size, ITEM_PER_PAGE)
                progress_bar.visibility = View.GONE
            })

            shouldFinish.observe(this@ListCharactersActivity, {
                if (it) finish()
            })
        }

        character_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.next?.let {
                        progress_bar.visibility = View.VISIBLE
                        layout.bringChildToFront(progress_bar)
                        viewModel.loadCharacters(recyclerView.context, it)
                    }
                }
            }
        })
    }

}