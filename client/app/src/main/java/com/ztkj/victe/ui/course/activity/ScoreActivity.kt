package com.ztkj.victe.ui.course.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.course.model.Examrecord
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_score.*
import kotlinx.android.synthetic.main.frag_line_item2.view.*

class ScoreActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        TongJi()
        tongji_head_text.text = "成绩查询"
        getData()
    }

    fun getData(){
        var map =  HashMap<String,String>()
        map.put("userid",MyApplication.user.userid.toString())
        RetrofitTools.post("getAllExamRecordByUser",map, Examrecord::class.java,object : RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var exams = succ as List<Examrecord>
                var adapter = Myadapter(exams)
                score_recy.layoutManager = LinearLayoutManager(this@ScoreActivity)
                score_recy.adapter = adapter
                adapter.setOnItemClickListener(object : BaseAdapterRecyclerView.OnItemClickListener{
                    override fun onItemClick(parent: RecyclerView, view: View, position: Int) {
                        var intent = Intent(this@ScoreActivity,ScoreDetailActivity::class.java);
                        intent.putExtra("examid",exams.get(position).examid)
                        startActivity(intent);

                    }

                })



            }

            override fun failure(msg: String) {
                toast("获取试卷列表失败")
            }

        })
    }

    class Myadapter(mList:List<Examrecord>): BaseAdapterRecyclerView<Examrecord>(mList,R.layout.frag_line_item2){
        override fun convert(
            holder: RecyclerHolder,
            item: Examrecord,
            position: Int,
            isScrolling: Boolean
        ) {
            with(holder.itemView){
                if (item.count == item.status){
                    frag_line_item2_tv.text = item.exam.name
                    frag_line_item2_status.text="${item.score}分"
                }else{
                    frag_line_item2_tv.text = item.exam.name
                    frag_line_item2_status.text="未批改"
                }

            }
        }

    }
}
