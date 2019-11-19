package com.example.itunestest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunestest.R
import com.example.itunestest.controllers.AlbumAdapter
import com.example.itunestest.model.MusicItem
import com.example.itunestest.model.MyResponse


class AlbumListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: ViewModelAlbum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(ViewModelAlbum::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_album_list, container, false)

        init(view)

        subscribeToData()
        subscribeToIsLoading()

        return view
    }

    private fun init(view: View){

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        progressBar = view.findViewById(R.id.ProgressBar)

        val seachView: SearchView = view.findViewById(R.id.searchView)
        seachView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                refresh(seachView.query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun subscribeToData() {
        viewModel.data.observe(this, Observer { data -> showData(data) })
    }

    private fun subscribeToIsLoading() {
        viewModel.isLoading.observe(this, Observer { isLoading -> showLoading(isLoading) })
    }

    fun refresh(term: String) {
        val term2 = term.replace(" ", "+")
        Log.d("refresh1", term2)
        viewModel.refresh(term2)
    }

    private fun showData(data: MyResponse) {
        if (data.response != null) {
            if (data.response?.code() == 200) {

                refreshRecycler(data.response?.body()!!.results)
                Log.d("onResponse", "OK ${data.response?.body()!!.results.size}")

            } else {
                Log.d("onResponse", "OPS ${data.response?.errorBody().toString()} ")
                Toast.makeText(context, "OPS", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("onFailure", data.t.toString())
            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshRecycler(list: List<MusicItem>) {
        if (list.isEmpty()) {
            Toast.makeText(context, "nothing found", Toast.LENGTH_SHORT).show()
        }
        val list2 = list.sortedBy { it.collectionName }
        val adapter = AlbumAdapter(list2) { partItem: MusicItem -> itemClicked(partItem) }
        recyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun itemClicked(album: MusicItem) {
        fragmentManager?.beginTransaction()!!
            .replace(R.id.fragment_container, AlbumFragment.newInstance(album))
            .addToBackStack(null)
            .commit()
    }
}