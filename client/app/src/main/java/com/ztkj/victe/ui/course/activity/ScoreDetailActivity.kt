package com.ztkj.victe.ui.course.activity

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
import kotlinx.android.synthetic.main.activity_score_detail.*
import kotlinx.android.synthetic.main.frag_line_item2.view.*

class ScoreDetailActivity : BaseActivity() {
    var examid = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_detail)
        TongJi()
        tongji_head_text.setText("试卷详情")
        examid = intent.getIntExtra("examid",0);
        getData();
    }
    fun getData(){
        var map =  HashMap<String,String>()
        map.put("userid", MyApplication.user.userid.toString())
        map.put("examid", examid.toString())
        RetrofitTools.post("getExamrecordByUserid",map, Examrecord::class.java,object : RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var exams = succ as List<Examrecord>
                var adapter = Myadapter(exams)
                score_detail_recy.layoutManager = LinearLayoutManager(this@ScoreDetailActivity)
                score_detail_recy.adapter = adapter
                adapter.setOnItemClickListener(object : BaseAdapterRecyclerView.OnItemClickListener{
                    override fun onItemClick(parent: RecyclerView, view: View, position: Int) {

                    }

                })



            }

            override fun failure(msg: String) {
                toast("获取试卷详情失败")
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

                frag_line_item2_tv.setText(item.question.name)
                frag_line_item2_status.setText("您的答案：${item.uanswer}    正确答案是：${item.question.answer}")

            }
        }

    }
}
