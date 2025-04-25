package com.ztkj.victe.ui.course.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.course.model.Details

/**
 * 课程章节详情
 */
class DetailFragemnt:LazyFragment(){
    companion object {
        fun newInstance(isLazyLoad: Boolean,detail:Details): DetailFragemnt {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            args.putSerializable("detail",detail);
            val fragment = DetailFragemnt()
            fragment.setArguments(args)
            return fragment
        }
    }
    //每次可见时会执行
    override fun onFragmentStartLazy() {
    }
    //每次从可见到不可见时执行
    override fun onFragmentStopLazy() {
        Log.e("TAG","-------onFragmentStopLazy-----------")
    }
    //初始化---关联布局文件
    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_course_detail)
        var detail = arguments!!.getSerializable("detail") as Details;
        contentView.findViewById<TextView>(R.id.frag_detail_content).setText("  ${detail.content}")
    }
    //第一次可见是执行---控件赋值
    override fun onResumeLazy() {
        Log.e("TAG","-------onResumeLazy-----------")

    }
    //界面销毁时执行
    override fun onDestroyViewLazy() {

    }

}