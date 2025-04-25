package com.ztkj.victe.ui.course.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mylhyl.circledialog.CircleDialog
import com.mylhyl.circledialog.view.listener.OnInputClickListener
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.discuss.Discuss
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.discuss_item.view.*
import kotlinx.android.synthetic.main.frag_course_discuss.*

/**
 * 课程章节评论
 */
class DiscussFragemnt:LazyFragment(){
    lateinit var targetid:String;
    companion object {
        fun newInstance(isLazyLoad: Boolean,targetid:String): DiscussFragemnt {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            args.putString("targetid",targetid)
            val fragment = DiscussFragemnt()
            fragment.setArguments(args)
            return fragment
        }
    }
    //每次可见时会执行
    override fun onFragmentStartLazy() {
    }
    //每次从可见到不可见时执行
    override fun onFragmentStopLazy() {

    }
    //初始化---关联布局文件
    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_course_discuss)

    }

    //第一次可见是执行---控件赋值
    override fun onResumeLazy() {
         targetid = arguments!!.getString("targetid");
        getDis(targetid);
        insert_discuss.setOnClickListener {
            CircleDialog.Builder(activity!!)
                .setTitle("评论")
                .setInputHint("请输入评论内容")
                .setPositiveInput("确定",object: OnInputClickListener {
                    override fun onClick(text: String?, v: View?) {
                        if (TextUtils.isEmpty(text)){
                            toast("内容不能为空")
                            return;
                        }
                        insertData(text.toString());
                    }
                })
                .setNegative("取消", null)
                .show()
        }
    }
    //界面销毁时执行
    override fun onDestroyViewLazy() {

    }

    fun insertData(content:String){
        var map = HashMap<String,String>();
        map.put("content",content)
        map.put("userid",MyApplication.user.userid.toString())
        map.put("targetid",targetid);

        RetrofitTools.post("insertDiscuss",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("true".equals(succ)){
                    toast("添加成功")
                    getDis(targetid);
                }
                else{
                    toast("添加失败")
                }
            }

            override fun failure(msg: String) {
                toast("添加失败")
            }

        })
    }


    fun getDis(targetid:String){
        var map = HashMap<String,String>();
        map.put("targetid",targetid)
        RetrofitTools.post("getAllDiscussByTargetId",map,Discuss::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var discuss = succ as List<Discuss>;
                course_detail_discuss_recy.layoutManager = LinearLayoutManager(applicationContext)
                course_detail_discuss_recy.adapter = MyAdapter(discuss)
            }

            override fun failure(msg: String) {
                toast("获取数据失败")
            }

        })
    }

    inner class MyAdapter(mlist:List<Discuss>): BaseAdapterRecyclerView<Discuss>(mlist,R.layout.discuss_item){
        override fun convert(holder: RecyclerHolder, item: Discuss, position: Int, isScrolling: Boolean) {
            with(holder.itemView){
                Glide.with(applicationContext).load(MyApplication.hostFile+item.user.headPic).error(
                    R.mipmap.ic_launcher).into(discuss_item_headpic)
                discuss_item_username.text = item.user.username
                discuss_item_content.text = item.content
                discuss_item_time.text = item.dtime
            }
        }

    }

}