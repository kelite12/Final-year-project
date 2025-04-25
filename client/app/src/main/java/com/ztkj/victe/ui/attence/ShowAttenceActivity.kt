package com.ztkj.victe.ui.attence

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_show_attence.*
import kotlinx.android.synthetic.main.frag_line_item2.view.*

class ShowAttenceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_attence)
        TongJi()
        tongji_head_text.setText("考勤信息")
        getData()

    }
    fun getData(){
        var map = HashMap<String,String>()
        var method = "getMyAttence"
//        if(MyApplication.user.userLevel==1){
            method = "getMyAttence"
            map.put("userid",MyApplication.user.userid.toString())
            attence_tv_total.visibility = View.GONE
//        }

        RetrofitTools.post(method,map, Attence::class.java,object: RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var news = succ as List<Attence>;
                //设置布局管理器
                show_attence_recy.layoutManager = LinearLayoutManager(applicationContext);
                //实例化适配器
                var adapter = Myadapter(news);
                //设置适配器
                show_attence_recy.adapter = adapter
                attence_tv_total.setText("考勤人数共计：${news.size}人")

            }

            override fun failure(msg: String) {
                toast(msg);
            }

        })
    }

    class Myadapter(mlist: List<Attence>):BaseAdapterRecyclerView<Attence>(mlist,R.layout.frag_line_item2){
        override fun convert(
            holder: RecyclerHolder,
            item: Attence,
            position: Int,
            isScrolling: Boolean
        ) {
            //Log.e("TAG",item.title);
            with(holder.itemView){
                frag_line_item2_tv.text = item.user.username
                frag_line_item2_status.text = "${item.time}  ${item.address}"


            }
        }

    }
}
