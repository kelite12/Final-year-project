package com.ztkj.victe.ui.userinfo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.activity_base.*

class AddUserActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        TongJi()
        tongji_head_text.setText("添加好友")
        add_userid_btn.setOnClickListener {
            if(TextUtils.isEmpty(add_userid.text)){
                toast("用户名不能为空")
                return@setOnClickListener
            }

            var map = HashMap<String,String>()
            map.put("senduserid","${MyApplication.user.userid}")
            map.put("username",add_userid.text.toString())
            RetrofitTools.get("insertFriends",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
                override fun <T> success(succ: T) {
                    if("true".equals(succ)){
                        toast("添加成功")
                        finish()
                    }else{
                        toast("添加失败")
                    }
                }

                override fun failure(msg: String) {

                }

            },false)
        }
    }
}