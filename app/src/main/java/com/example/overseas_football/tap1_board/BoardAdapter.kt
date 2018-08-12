package com.example.overseas_football.tap1_board

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.R
import com.example.overseas_football.base.BaseActivity
import com.example.overseas_football.model.Board
import com.example.overseas_football.utill.Shared
import kotlinx.android.synthetic.main.board_item.view.*


class BoardAdapter(val activity: Activity, private val recyclerviewPositionListener: RecyclerviewPositionListener) : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {
    val itemList = ArrayList<Board>()
    var isMoreData = false

    interface RecyclerviewPositionListener {
        fun lastPosition(position: Int)
        fun like(num: Int)
        fun cancelLike(num: Int)
        fun removeItem(num: Int, position: Int, board: Board)
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

                // 사용자 프로필
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

                // 게시물 이미지
                itemList[position].img.let {
                    if (it != null) {
                        circleimg_profile.background = null
                        Glide.with(context)
                                .load(BuildConfig.BASE_URL + "glideProfile?img=" + it)
                                .apply(RequestOptions().placeholder(ContextCompat.getDrawable(context, R.drawable.defalut_profile_img)))
                                .into(circleimg_profile)
                    } else {
                        Glide.with(context).clear(circleimg_profile)
                        circleimg_profile.background = ContextCompat.getDrawable(context, R.drawable.defalut_profile_img)
                    }
                }

                itemList[position].b_like.let {
                    tv_count_like.text = it.toString() + " 개"
                }

                // 내가 좋아요를 눌른 게시물인지
                itemList[position].islike.let {
                    val textColor: Int
                    val background: Int
                    if (itemList[position].islike == "1") {
                        textColor = R.color.white
                        background = R.drawable.rectangle_blue_like

                    } else {
                        textColor = R.color.black
                        background = R.drawable.rectangle_white_outside_blue
                    }
                    tv_like.setTextColor(ContextCompat.getColor(context, textColor))
                    tv_count_like.setTextColor(ContextCompat.getColor(context, textColor))
                    linear_like.background = ContextCompat.getDrawable(context, background)
                }

                // 좋아요
                linear_like.setOnClickListener {
                    val dump: Int
                    val textColor: Int
                    val background: Int
                    if (itemList[position].islike == "1") {
                        recyclerviewPositionListener.cancelLike(itemList[position].num)
                        dump = -1
                        textColor = R.color.black
                        background = R.drawable.rectangle_white_outside_blue
                        itemList[position].islike = "0"
                    } else {
                        recyclerviewPositionListener.like(itemList[position].num)
                        dump = 1
                        textColor = R.color.white
                        background = R.drawable.rectangle_blue_like
                        itemList[position].islike = "1"
                    }
                    tv_count_like.text = (tv_count_like.text.split(" ")[0].toInt() + dump).toString() + " 개"
                    tv_like.setTextColor(ContextCompat.getColor(context, textColor))
                    tv_count_like.setTextColor(ContextCompat.getColor(context, textColor))
                    linear_like.background = ContextCompat.getDrawable(context, background)
                }

                linear_comment.setOnClickListener {
                    activity.startActivity(Intent(context, CommentActivity::class.java))
                    activity.overridePendingTransition(R.anim.bottom_down, R.anim.bottom_up)
                }

                img_more.setOnClickListener {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setItems(R.array.more, { dialog, which ->
                        when (which) {
                            0 -> context.startActivity(Intent(context, WriteActivity::class.java).putExtra("board", itemList[position]))
                            1 -> {
                                (activity as BaseActivity)
                                        .showDialog("삭제하시겠습니까?")
                                        .onPositive { dialog, which ->
                                            recyclerviewPositionListener.removeItem(itemList[position].num, position, itemList[position])
                                            dialog.dismiss()
                                        }.show()
                            }
                        }
                    })
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

}