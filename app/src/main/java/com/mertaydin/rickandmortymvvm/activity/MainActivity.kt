package com.mertaydin.rickandmortymvvm.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mertaydin.rickandmortymvvm.R
import com.mertaydin.rickandmortymvvm.adapter.RecyclerViewAdapter
import com.mertaydin.rickandmortymvvm.util.ViewModelFactory
import com.mertaydin.rickandmortymvvm.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelFactory()).get(CharacterViewModel::class.java)

        viewModel.apply {
            loadCharacters(this@MainActivity)

            list.observe(this@MainActivity, {
                if (recycler_view.adapter == null)
                    recycler_view.adapter = RecyclerViewAdapter(it).apply {
                        onItemClick = {
                            println(it.toString())
                            // TODO: detail activity
                        }
                    }
                else
                    recycler_view.adapter!!.notifyItemRangeInserted(viewModel.list.value!!.size, 20)
                progress_bar.visibility = View.GONE
            })

            shouldFinish.observe(this@MainActivity, {
                if (it) finish()
            })
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.next?.let {
                        progress_bar.visibility = View.VISIBLE
                        viewModel.loadCharacters(recyclerView.context, it)
                    }
                }
            }
        })
    }

}