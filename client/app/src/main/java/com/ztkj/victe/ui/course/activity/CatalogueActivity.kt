package com.ztkj.victe.ui.course.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.course.model.Details
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_catalogue.*
import kotlinx.android.synthetic.main.details_recy_item.view.*

/**
 * 课程章节
 */
class CatalogueActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)
        TongJi()
        tongji_head_text.setText("目录详情")
        var courseId = intent.getStringExtra("courseid");
        if(courseId==null){
           // Log.e("TAG","------------空值-----------")
            courseId = "0";
        }
        getdata(courseId);
    }

    fun getdata(courseId:String){
        var map = HashMap<String,String>();
        map.put("courseid",courseId);
        RetrofitTools.post("getDetailsByCourseid",map,Details::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var details = succ as List<Details>;
                var adapter = Myadapter(details);
                catalogue_recy.layoutManager = LinearLayoutManager(applicationContext)
                catalogue_recy.addItemDecoration(DividerItemDecoration(this@CatalogueActivity,DividerItemDecoration.VERTICAL))
                catalogue_recy.adapter = adapter;
                adapter.setOnItemClickListener(object:BaseAdapterRecyclerView.OnItemClickListener{
                    override fun onItemClick(parent: RecyclerView, view: View, position: Int) {
                        var intent = Intent(this@CatalogueActivity,CourseDetailActivity::class.java);
                        intent.putExtra("detail",details.get(position))
                        startActivity(intent);

                    }

                })

            }

            override fun failure(msg: String) {
                toast("获取目录失败")
            }

        })
    }

    class Myadapter(mList:List<Details>) :BaseAdapterRecyclerView<Details>(mList,R.layout.details_recy_item){
        override fun convert(
            holder: RecyclerHolder,
            item: Details,
            position: Int,
            isScrolling: Boolean
        ) {
            with(holder.itemView){
                catalogue_recy_item_tv.setText(item.title)
            }
        }

    }
}
