package com.example.overseas_football.tap1_board

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.R
import com.example.overseas_football.model.Board
import com.example.overseas_football.utill.Shared
import kotlinx.android.synthetic.main.board_item.view.*


class BoardAdapter(val activity: Activity, private val recyclerviewPositionListener: RecyclerviewPositionListener) : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {
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
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.board_item, parent, false))
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
                tv_nickname.text = itemList[position].nickname
                tv_date.text = itemList[position].b_time
                tv_content.text = itemList[position].b_content

                itemList[position].b_email.let {
                    if (it == Shared().getUser(context)?.email) {
                        img_more.visibility = View.VISIBLE
                    } else {
                        img_more.visibility = View.GONE
                    }
                }
                itemList[position].b_img.let {
                    if (it != null) {
                        img.visibility = View.VISIBLE
                        Glide.with(context)
                                .load(BuildConfig.BASE_URL + "glideBoard?img=" + it)
                                .into(object : SimpleTarget<Drawable>() {
                                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                        img.setImageDrawable(resource)
                                        img.requestLayout()
                                    }
                                })
                    } else {
                        Glide.with(context).clear(img)
                        img.visibility = View.GONE
                    }
                }

                itemList[position].img.let {
                    if (it != null) {
                        circleimg_profile.background = null
                        Glide.with(context)
                                .load(BuildConfig.BASE_URL + "glideProfile?img=" + it)
                                .apply(RequestOptions().placeholder(ContextCompat.getDrawable(context, R.drawable.defalut_profile_img)))
                                .into(object : SimpleTarget<Drawable>() {
                                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                        circleimg_profile.setImageDrawable(resource)
                                        circleimg_profile.requestLayout()
                                    }

                                })
                    } else {
                        Glide.with(context).clear(circleimg_profile)
                        circleimg_profile.background = ContextCompat.getDrawable(context, R.drawable.defalut_profile_img)
                    }
                }

                itemList[position].b_like.let {
                    tv_count_like.text=it.toString()+" 개"
                }

                linear_comment.setOnClickListener {
                    activity.startActivity(Intent(context, CommentActivity::class.java))
                    activity.overridePendingTransition(R.anim.bottom_down, R.anim.bottom_up)
                }
            }
        }
    }

}