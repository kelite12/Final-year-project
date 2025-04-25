package com.ztkj.victe.ui.attence

import android.os.Bundle
import android.text.TextUtils
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_insert_ask.*

class InsertAskActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_ask)
        TongJi()
        tongji_head_text.setText("请假")
        attence_submit.setOnClickListener {
            if(TextUtils.isEmpty(attence_remark1.text)){
                toast("请假理由不能为空")
                return@setOnClickListener
            }
            var map = HashMap<String,String>()
            map.put("remark1",attence_remark1.text.toString())
            map.put("userid",MyApplication.user.userid.toString())
            map.put("type","1")
            RetrofitTools.get("insertAttence",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
                override fun failure(msg: String) {
                    toast(msg)

                }

                override fun <T> success(succ: T) {
                    if("true".equals(succ)){
                        toast("提交成功")
                        finish()
                    }else{
                        toast("今日已提交申请了")
                    }
                }

            },false)
        }



    }
}
