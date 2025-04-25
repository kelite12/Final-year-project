package com.ztkj.victe.ui.course.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.course.model.Course
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.course_study_recy_item.view.*
import kotlinx.android.synthetic.main.frag_course_study.*

/**
 * 课程
 */

class CourseStudyFragment:LazyFragment(){
    companion object {
        fun newInstance(isLazyLoad: Boolean): CourseStudyFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = CourseStudyFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onFragmentStartLazy() {
    }

    override fun onFragmentStopLazy() {
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_course_study)
    }

    override fun onResumeLazy() {
        getdata();
    }

    //获取网络数据
    fun getdata(){
        RetrofitTools.post("getAllOkCourse",Course::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var courses = succ as List<Course>;
                var adapter = Myadapter(courses)
                course_study_recy.layoutManager = GridLayoutManager(applicationContext,2)
                course_study_recy.adapter = adapter;
                adapter.setOnItemClickListener(object :BaseAdapterRecyclerView.OnItemClickListener{
                    override fun onItemClick(parent: RecyclerView, view: View, position: Int) {
                        var intent = Intent(activity,CatalogueActivity::class.java);
                        //Log.e("TAG","-------${courses.get(position).courseid}------")
                        intent.putExtra("courseid",courses.get(position).courseid.toString());
                        startActivity(intent)

                    }

                })

            }

            override fun failure(msg: String) {
                toast("获取网络课程失败")
            }

        })
    }

    override fun onDestroyViewLazy() {
    }
    class Myadapter(mList:List<Course>): BaseAdapterRecyclerView<Course>(mList,R.layout.course_study_recy_item){
        override fun convert(
            holder: RecyclerHolder,
            item: Course,
            position: Int,
            isScrolling: Boolean
        ) {
            with(holder.itemView){
                course_study_recy_item_tv.text = item.coursename
                Glide.with(holder.itemView.context).load(MyApplication.hostFile+item.pic).error(R.mipmap.ic_launcher).into(course_study_recy_item_iv)
            }
        }

    }
}