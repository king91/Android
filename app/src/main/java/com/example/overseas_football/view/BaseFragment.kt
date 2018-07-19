package com.example.overseas_football.view


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.View
import android.widget.LinearLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.overseas_football.R
import com.example.overseas_football.network.Constants
import com.example.overseas_football.view.utill.Shared
import com.github.clans.fab.FloatingActionMenu
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.tab3.*

open class BaseFragment : Fragment() {
    open fun openBasicDialog(context: Context, title: String, content: String): MaterialDialog.Builder {
        return MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .negativeText("취소")
                .positiveText("확인")
    }
    fun loginOrBeloginView(context: Context, linearLayout: LinearLayout, floatingActionMenu: FloatingActionMenu) {
        if (Shared().getUser(context) != null) {
            linearLayout.visibility = View.GONE
            floatingActionMenu.visibility = View.VISIBLE
        } else {
            linearLayout.visibility = View.VISIBLE
            floatingActionMenu.visibility = View.GONE
        }
    }
    fun isLoginViewCheck(linearLayout_Login:LinearLayout,linearLayout_beLogin:LinearLayout,circleimg_profile:CircleImageView){
        val user = Shared().getUser(requireContext())
        if (user != null) {
            linearLayout_Login.visibility = View.VISIBLE
            linearLayout_beLogin.visibility = View.GONE
            //사용자 프로필 이미지 유무
            val loadValue: Any
            if (user.img != null) {
                circleimg_profile.background = null
                loadValue = Constants.BASE_URL + "glideProfile?img=" + user.img
            } else {
                loadValue = R.drawable.defalut_profile_img
            }
            Glide.with(requireActivity())
                    .load(loadValue)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .into(circleimg_profile)
            tv_nickname.text = user.nickname
        } else {
            linearLayout_Login.visibility = View.GONE
            linearLayout_beLogin.visibility = View.VISIBLE
        }
    }
    fun <T> startActivity(activity: Activity, nextActivity: Class<T>) {
        activity.startActivity(Intent(activity, nextActivity))
    }
}