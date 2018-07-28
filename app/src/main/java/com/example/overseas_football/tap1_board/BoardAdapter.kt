package com.example.overseas_football.tap1_board

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.R
import com.example.overseas_football.model.Board
import kotlinx.android.synthetic.main.board_item.view.*

class BoardAdapter(val context: Context, private val recyclerviewPositionListener: RecyclerviewPositionListener) : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {
    val itemList = ArrayList<Board>()
    var isMoreData = false

    interface RecyclerviewPositionListener {
        fun lastPosition(position: Int)
    }

    fun addItem(_itemList: List<Board>, moredata: Boolean) {
        isMoreData = moredata
        for (item in _itemList) {
            itemList.add(item)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.board_item, parent, false))
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == itemCount - 1 && isMoreData) {
            recyclerviewPositionListener.lastPosition(position)
        }
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            with(itemView) {
                tv_nickname.text = itemList[position].b_nickname
                tv_date.text = itemList[position].b_time
                tv_content.text = itemList[position].b_content


                itemList[position].b_img.let {
                    if (it != null) {
                        img.visibility = View.VISIBLE
                        Glide.with(context)
                                .load(BuildConfig.BASE_URL + "glideBoard?img=" + it)
                                .into(img)
                    } else {
                        Glide.with(context).clear(img)
                        img.visibility = View.GONE
                    }
                }
            }
        }
    }

}