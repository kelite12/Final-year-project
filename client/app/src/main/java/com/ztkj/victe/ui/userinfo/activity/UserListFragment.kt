package com.ztkj.victe.ui.userinfo.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.userinfo.model.User
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import io.rong.imkit.RongIM
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.fornum_item.view.*
import kotlinx.android.synthetic.main.frag_userlist.*
import java.util.*
import kotlin.collections.HashMap

class UserListFragment :LazyFragment(){
    companion object {
        fun newInstance(isLazyLoad: Boolean): UserListFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = UserListFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onFragmentStartLazy() {
        getdata()
    }

    override fun onFragmentStopLazy() {

    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
//        setContentView(R.layout.frag_userlist)
        getdata()

    }
    fun getdata(){
        var map = HashMap<String,String>()
//        map.put("","")
        RetrofitTools.post("getAllUser",map,User::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var userList = succ as ArrayList<User>
//                for(us in userList.s){
//                    if(us.userid==MyApplication.user.userid){
//                        userList.remove(us)
//                    }
//                }
                recy_userlist.layoutManager = LinearLayoutManager(applicationContext)
                var adapter = Myadapter(userList)
                recy_userlist.adapter = adapter
                adapter.setOnItemClickListener(object :BaseAdapterRecyclerView.OnItemClickListener{
                    override fun onItemClick(parent: RecyclerView, view: View, position: Int) {
//                        var conversationType = Conversation.ConversationType.PRIVATE;
//                        val uri = Uri.parse("rong://" + applicationContext.getApplicationInfo().processName).buildUpon().appendPath("conversation").appendPath(conversationType.getName().toLowerCase(
//                            Locale.US)).appendQueryParameter("targetId", userList.get(position).username).appendQueryParameter("title", "家长讨论区").build()
//                        var intent = Intent("android.intent.action.VIEW", uri);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        intent.putExtra("username",userList.get(position).username)
//                        startActivity(intent)
                        RongIM.getInstance().setMessageAttachedUserInfo(true);
                        RongIM.getInstance().startPrivateChat(getActivity(), "${userList.get(position).username}", userList.get(position).username);
                    }

                })

            }

            override fun failure(msg: String) {
               toast(msg)
            }

        })


    }

    class Myadapter(mList:List<User>): BaseAdapterRecyclerView<User>(mList,R.layout.fornum_item){
        override fun convert(
            holder: RecyclerHolder,
            item: User,
            position: Int,
            isScrolling: Boolean
        ) {
            with(holder.itemView){
//                fornum_item_time.setText(item.sendtime)
                fornum_item_head_content.setText(item.sex)
                fornum_item_head_name.setText(item.username)
                Glide.with(holder.itemView.context).load(MyApplication.hostFile+item.headPic).error(R.mipmap.ic_launcher).into(fornum_item_head_pic)
                fornum_item_time.visibility = View.GONE
                if(MyApplication.user.userid==item.userid){
                    fornum_item_head_name.setText(item.username+"(本人)")
                }
            }
        }

    }

    override fun onResumeLazy() {

    }

    override fun onDestroyViewLazy() {

    }

}