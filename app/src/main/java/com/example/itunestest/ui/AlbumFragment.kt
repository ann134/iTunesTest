package com.example.itunestest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunestest.R
import com.example.itunestest.controllers.SongsAdapter
import com.example.itunestest.model.MusicItem
import com.example.itunestest.model.MyResponse
import com.example.itunestest.util.PicassoUtil


class AlbumFragment : Fragment() {

    companion object {
        fun newInstance(album: MusicItem): Fragment {
            val fragment = AlbumFragment()
            val args = Bundle()
            args.putParcelable("album", album)
            fragment.arguments = args
            return fragment
        }
    }

    private var album: MusicItem? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: ViewModelSong

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        album = arguments?.getParcelable("album") as MusicItem?
        viewModel = ViewModelProviders.of(this).get(ViewModelSong::class.java)
        album?.collectionId?.let { viewModel.refresh(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_album, container, false)

        init(view)

        subscribeToIsLoading()
        subscribeToData()

        return view
    }

    private fun init(view: View){
        progressBar = view.findViewById(R.id.progress_bar)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val textViewName = view.findViewById(R.id.tv_name) as TextView
        val textViewArtist = view.findViewById(R.id.tv_artist) as TextView
        val myImageView: ImageView = view.findViewById(R.id.imv_avatar)
        textViewName.text = album?.artistName
        textViewArtist.text = album?.collectionName
        PicassoUtil.getPicasso().load(album?.artworkUrl100).into(myImageView)
    }

    private fun subscribeToData() {
        viewModel.dataSongs.observe(this, Observer { data -> showData(data) })
    }

    private fun subscribeToIsLoading() {
        viewModel.isLoadingSongs.observe(this, Observer { isLoading -> showLoading(isLoading) })
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
        val list2 = list.filter { musicItem -> musicItem.trackName != null }
        val adapter = SongsAdapter(list2)
        recyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}