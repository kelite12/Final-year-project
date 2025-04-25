package com.ztkj.victe.ui.userinfo.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mylhyl.circledialog.CircleDialog
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.userinfo.model.Friends
import com.ztkj.victe.ui.userinfo.model.User
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.frag_users.*
import kotlinx.android.synthetic.main.user_recy_item.view.*
import java.util.*
import kotlin.collections.HashMap

class UsersFragment : LazyFragment() {
    companion object {
        fun newInstance(isLazyLoad: Boolean): UsersFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = UsersFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onFragmentStartLazy() {
        getData();
    }

    override fun onFragmentStopLazy() {
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_users)
    }

    override fun onResumeLazy() {
        getData();

        add_friends.setOnClickListener {
            startActivity(Intent(activity,AddUserActivity::class.java))
        }
    }

    override fun onDestroyViewLazy() {
    }

    fun getData() {
        var map = HashMap<String,String>()
        map.put("senduserid","${MyApplication.user.userid.toString()}")
//        if(MyApplication.user.userLevel==1){
//            map.put("userLevel","2")
//        }else{
//            map.put("userLevel","1")
//        }

        RetrofitTools.post(
            "getAllUsers",map,
            User::class.java,
            object : RetrofitTools.IRetrofitResponse {
                override fun <T> success(succ: T) {
                    var news = succ as List<User>;
                    //设置布局管理器
                    users_recy.layoutManager = LinearLayoutManager(applicationContext);
                    //实例化适配器
                    var adapter = Myadapter(news);
                    //设置适配器
                    users_recy.adapter = adapter
                    //设置item点击事件
                    adapter.setOnItemClickListener(object :
                        BaseAdapterRecyclerView.OnItemClickListener {
                        override fun onItemClick(parent: RecyclerView, view: View, position: Int) {
                            MyApplication.messages.clear()

                            var conversationType = Conversation.ConversationType.PRIVATE;

                                val uri =
                                    Uri.parse("rong://" + applicationContext.getApplicationInfo().processName)
                                        .buildUpon().appendPath("conversation")
                                        .appendPath(conversationType.getName().toLowerCase(Locale.US))
                                        .appendQueryParameter("targetId", news.get(position).username)
                                        .appendQueryParameter("title", "${news.get(position).username}").build()
                                var intent = Intent("android.intent.action.VIEW", uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.putExtra("username", news.get(position).username)
                                startActivity(intent)





                        }

                    })

                    adapter.setOnItemLongClickListener(object :BaseAdapterRecyclerView.OnItemLongClickListener{
                        override fun onItemLongClick(
                            parent: RecyclerView,
                            view: View,
                            position: Int
                        ): Boolean {



                            return true
                        }

                    })
                }

                override fun failure(msg: String) {
                    toast(msg);
                }

            })

    }

    //增删改
    fun delete(goodsid:String){
        var map = HashMap<String,String>()
        map.put("friendsid",goodsid)
        RetrofitTools.get("deleteFriends",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("true".equals(succ)){
                    toast("Delete successfully")
                    getData();
                }else{
                    toast("Delete failed")
                }
            }
            override fun failure(msg: String) {
                toast(msg)
            }
        },false)
    }

    class Myadapter(mlist: List<User>) :
        BaseAdapterRecyclerView<User>(mlist, R.layout.user_recy_item) {
        override fun convert(
            holder: RecyclerHolder,
            item: User,
            position: Int,
            isScrolling: Boolean
        ) {
            //Log.e("TAG",item.title);
            with(holder.itemView) {
                user_recy_tv.setText(item.username)
                Glide.with(holder.itemView.context).load(MyApplication.hostFile + item.headPic)
                    .error(R.mipmap.ic_launcher).into(user_recy_ci)
//                if(item.user.username==MyApplication.user.username){
//                    user_recy_tv.setText(item.user2.username)
//                }else{
//                    user_recy_tv.setText(item.user.username)
//                }

//                if (item.userLevel==1){
                    user_recy_tv.setText(item.username)
//                }else if(item.userLevel==2){
//                    user_recy_tv.setText("${item.username}(医生)")
//                }else if(item.userLevel==3){
//                    user_recy_tv.setText("${item.username}(管理员)")
//                }

                if(MyApplication.messages.contains(item.username)){
                    news_msg_tv.visibility = View.VISIBLE
                }else{
                    news_msg_tv.visibility = View.GONE
                }

            }
        }

    }


}