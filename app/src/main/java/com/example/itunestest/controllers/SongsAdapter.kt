package com.example.itunestest.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itunestest.R
import com.example.itunestest.model.MusicItem

class SongsAdapter(private val userList: List<MusicItem>) :
    RecyclerView.Adapter<SongsAdapter.SongHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongHolder(v)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class SongHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(album: MusicItem) {
            val textViewName = itemView.findViewById(R.id.tv_name) as TextView
            textViewName.text = "${album.trackNumber}. ${album.trackName}"
        }
    }
}