package com.ztkj.victe.ui.discuss

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_showdiscuss.*
import kotlinx.android.synthetic.main.discuss_item.view.*

class ShowdiscussActivity : BaseActivity() {
    var discuss  = ArrayList<Discuss>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showdiscuss)
        TongJi()
        tongji_head_text.text="查看评论"
        getDis(intent.getStringExtra("movieid"))
    }

    fun getDis(movieid:String){
        var map = HashMap<String,String>();
        map.put("targetid",movieid)
        RetrofitTools.post("getAllDiscussByTargetId",map,Discuss::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
//                discuss.clear();
                discuss = succ as ArrayList<Discuss>;
                var adapter = MyAdapter(discuss);
                recy_discuss_movie.layoutManager = LinearLayoutManager(applicationContext)
                recy_discuss_movie.adapter = adapter;
            }

            override fun failure(msg: String) {

            }

        })

    }


    inner class MyAdapter(mlist:List<Discuss>): BaseAdapterRecyclerView<Discuss>(mlist,R.layout.discuss_item){
        override fun convert(holder: RecyclerHolder, item: Discuss, position: Int, isScrolling: Boolean) {
            with(holder.itemView){
                Glide.with(applicationContext).load(MyApplication.hostFile+item.user.headPic).error(R.mipmap.ic_launcher).into(discuss_item_headpic)
                discuss_item_username.text = item.user.username
                discuss_item_content.text = item.content
                discuss_item_time.text = item.dtime
            }
        }

    }


}
