package com.ztkj.victe.ui.fornum.activity

import androidx.appcompat.app.AppCompatActivity
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
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.discuss.Discuss
import com.ztkj.victe.ui.fornum.model.Fornum
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_fornum_detail.*
import kotlinx.android.synthetic.main.discuss_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FornumDetailActivity : BaseActivity() {
    lateinit var fornum:Fornum;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fornum_detail)
        TongJi()
        tongji_head_text.setText("详情")
        fornum = intent.getSerializableExtra("fornum") as Fornum;
        Glide.with(applicationContext).load(MyApplication.hostFile+fornum.user.headPic).error(R.mipmap.ic_launcher).into(show_fornum_user_headpic)
        show_fornum_username.setText(fornum.user.username)
        show_fornum_content.setText(fornum.content)
        var images = fornum.urls.split("#").toTypedArray()
        for ((position,item) in images.withIndex()){
            if (!item.startsWith("http")){
                images[position] = "${MyApplication.hostFile}${item}"
            }
        }
        show_fornum_mulity.setList(images.toMutableList())
        getDis();
        insert_discuss.setOnClickListener {
            CircleDialog.Builder(this)
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

    fun insertData(content:String){
        var map = HashMap<String,String>();
        map.put("content",content)
        map.put("userid",MyApplication.user.userid.toString())
        map.put("targetid",fornum.fornumid.toString());

        RetrofitTools.post("insertDiscuss",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("true".equals(succ)){
                    toast("添加成功")
                    getDis();
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


    fun getDis(){
        var map = HashMap<String,String>();
        map.put("targetid",fornum.fornumid.toString())
        RetrofitTools.post("getAllDiscussByTargetId",map,Discuss::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var discuss = succ as List<Discuss>;
                show_fornum_recy.layoutManager = LinearLayoutManager(applicationContext)
                show_fornum_recy.adapter = MyAdapter(discuss)
            }

            override fun failure(msg: String) {
                toast("获取数据失败")
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
