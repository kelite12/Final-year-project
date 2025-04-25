package com.ztkj.victe.ui.course.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.course.model.Exam
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_exam_list.*
import kotlinx.android.synthetic.main.frag_line_item.view.*

class ExamListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_list)
        TongJi()
        tongji_head_text.setText("试卷列表")
        getData();
    }
    fun getData(){
        RetrofitTools.post("getAllExamByStatues",Exam::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var exams = succ as List<Exam>;
                var adapter = Myadapter(exams)
                exam_list_recy.layoutManager = LinearLayoutManager(this@ExamListActivity);
                exam_list_recy.adapter = adapter
                adapter.setOnItemClickListener(object :BaseAdapterRecyclerView.OnItemClickListener{
                    override fun onItemClick(parent: RecyclerView, view: View, position: Int) {
                        var intent = Intent(this@ExamListActivity,ExamActivity::class.java);
                        intent.putExtra("examid",exams[position].examid.toString())
                        startActivity(intent)
                    }

                })



            }

            override fun failure(msg: String) {
                toast("获取试卷列表失败")
            }

        })
    }

    class Myadapter(mList:List<Exam>): BaseAdapterRecyclerView<Exam>(mList,R.layout.frag_line_item){
        override fun convert(
            holder: RecyclerHolder,
            item: Exam,
            position: Int,
            isScrolling: Boolean
        ) {
            with(holder.itemView){
                frag_line_item_tv.setText(item.name);
            }
        }

    }
}
