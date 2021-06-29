package com.mertaydin.rickandmortymvvm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager

                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1)
                    viewModel.next?.let {
                        progress_bar.visibility = View.VISIBLE
                        viewModel.loadCharacters(recyclerView.context, it)
                    }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

}