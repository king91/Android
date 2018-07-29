package com.example.overseas_football.tap2_news

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.overseas_football.R
import com.example.overseas_football.model.Articles
import kotlinx.android.synthetic.main.board_item.view.*
import kotlinx.android.synthetic.main.news_view.view.*
import java.io.FileNotFoundException


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
            val target = object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    itemView.imageview.setImageDrawable(resource)
                    itemView.imageview.requestLayout()
                }
            }
            Glide.with(context).load(list[position].urlToImage).into(target)
            itemView.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(list[position].url)))
            }
        }
    }
}