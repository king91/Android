package com.example.overseas_football.tap2_news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.overseas_football.R
import com.example.overseas_football.model.Articles
import kotlinx.android.synthetic.main.news_view.view.*


class NewsAdapter(var context: Context, var list: ArrayList<Articles>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(position: Int) {
            itemView.textview_title.text = list[position].title
            itemView.textview_description.text = list[position].description
            Glide.with(context).load(list[position].urlToImage).into(itemView.imageview).view.scaleType = ImageView.ScaleType.FIT_XY
            itemView.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(list[position].url)))
            }
        }
    }
}