package com.example.overseas_football.tap1_board.comment

import android.app.AlertDialog
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.overseas_football.BuildConfig
import com.example.overseas_football.R
import com.example.overseas_football.model.Comment
import com.example.overseas_football.utill.Shared
import kotlinx.android.synthetic.main.comment_item.view.*
import java.util.*

class Adapter(val context: Context, private val recyclerviewPositionListener: RecyclerviewPositionListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    val itemlist = ArrayList<Comment>()


    interface RecyclerviewPositionListener {
        fun removeItem(c_index: Int, position: Int, comment: Comment)
        fun commentItemUpdate(position: Int, comment: Comment)
    }

    fun addItems(comment: List<Comment>) {
        itemlist.clear()
        for (content in comment) {
            itemlist.add(content)
        }
        notifyDataSetChanged()
    }

    fun addItem(item: Comment) {
        itemlist.add(item)
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false))
    }

    override fun getItemCount(): Int = itemlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemlist[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: Comment, position: Int) {
            with(itemView) {
                if (comment.c_index == -1) {
                    progressbar.visibility = View.VISIBLE
                    constraint.visibility = View.GONE
                } else {
                    progressbar.visibility = View.GONE
                    constraint.visibility = View.VISIBLE

                    tv_comment.visibility = View.VISIBLE
                    edit_item_comment.visibility = View.GONE
                    tv_comment_update.visibility = View.GONE

                    tv_comment.text = comment.c_comment
                    tv_date.text = comment.c_time
                    tv_nickname.text = comment.nickname

                    comment.c_email.let {
                        if (it == Shared().getUser(context)?.email) {
                            img_more.visibility = View.VISIBLE
                        } else {
                            img_more.visibility = View.GONE
                        }
                    }
                    comment.img.let {
                        if (!it.isNullOrEmpty()) {
                            img_profile.background = null
                            Glide.with(context)
                                    .load(BuildConfig.BASE_URL + "glideProfile?img=" + it)
                                    .apply(RequestOptions().placeholder(ContextCompat.getDrawable(context, R.drawable.defalut_profile_img)))
                                    .into(img_profile)
                        } else {
                            Glide.with(context).clear(img_profile)
                            img_profile.visibility = View.GONE
                        }
                    }

                    img_more.setOnClickListener {
                        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                        builder.setItems(R.array.more, { dialog, which ->
                            when (which) {
                                0 -> {
                                    tv_comment.visibility = View.GONE
                                    edit_item_comment.visibility = View.VISIBLE
                                    tv_comment_update.visibility = View.VISIBLE

                                    edit_item_comment.setText(tv_comment.text.toString())
                                }
                                1 -> {
                                    (context as CommentActivity)
                                            .showDialog("삭제하시겠습니까?")
                                            .onPositive { dialog, which ->
                                                recyclerviewPositionListener.removeItem(comment.c_index, position, comment)
                                                dialog.dismiss()
                                            }.show()
                                }
                            }
                        })
                        val dialog = builder.create()
                        dialog.show()
                    }

                    tv_comment_update.setOnClickListener {
                        comment.c_comment = edit_item_comment.text.toString()
                        recyclerviewPositionListener.commentItemUpdate(position, comment)
                    }
                }
            }
        }
    }
}