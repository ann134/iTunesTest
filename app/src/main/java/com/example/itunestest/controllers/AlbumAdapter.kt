package com.example.itunestest.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itunestest.R
import com.example.itunestest.model.MusicItem
import com.example.itunestest.util.PicassoUtil

class AlbumAdapter(val userList: List<MusicItem>, val clickListener: (MusicItem) -> Unit) :
    RecyclerView.Adapter<AlbumAdapter.AlbumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumHolder(v)
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.bindItems(userList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(album: MusicItem, clickListener: (MusicItem) -> Unit) {
            val textViewName = itemView.findViewById(R.id.tv_name) as TextView
            val textViewArtist = itemView.findViewById(R.id.tv_artist) as TextView
            val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.imv_avatar)
            textViewName.text = album.artistName
            textViewArtist.text = album.collectionName
            PicassoUtil.getPicasso().load(album.artworkUrl100).into(myImageView)

            itemView.setOnClickListener { clickListener(album) }
        }
    }
}