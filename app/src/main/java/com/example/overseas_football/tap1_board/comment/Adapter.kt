package com.example.overseas_football.tap1_board.comment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.overseas_football.R
import com.example.overseas_football.model.Comment
import kotlinx.android.synthetic.main.comment_item.view.*

class Adapter(val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    val itemlist = ArrayList<Comment>()
    fun addItems(comment: List<Comment>) {
        for (content in comment) {
            itemlist.add(content)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false))
    }

    override fun getItemCount(): Int = itemlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemlist[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: Comment) {
            with(itemView) {
                tv_comment.text = comment.c_comment
            }
        }
    }
}