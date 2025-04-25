package com.ztkj.victe.ui.userinfo

import android.os.Bundle
import android.text.TextUtils
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_update_pass.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePassActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pass)
        TongJi()
        tongji_head_text.text = "Change Password"
        update_login_text.setOnClickListener {
            if(TextUtils.isEmpty(update_pass_old_edittext.text)){
                toast("The original password cannot be empty")
            }
            else if(TextUtils.isEmpty(update_pass_new_edittext.text)){
                toast("The new password cannot be empty")
            }
            else if(!update_pass_new_edittext.text.toString().trim().equals(update_pass_renew_edittext.text.toString().trim())){
                toast("Two password entries and exits are inconsistent")
            }
            else if (!MyApplication.user.password!!.equals(update_pass_old_edittext.text.toString())){
                toast("Original password input error")
            }
            else{
                var map = HashMap<String,String>();
                var parts = ArrayList<MultipartBody.Part>()
                map.put("userid",MyApplication.user.userid.toString())
                map.put("password",update_pass_renew_edittext.text.toString())
                RetrofitTools.upload("updateUser",map,parts,object :RetrofitTools.IRetrofitResponse{
                    override fun <T> success(succ: T) {
                        if ("true".equals(succ)){
                            toast("Password changed successfully")
                            finish()
                        }
                        else{
                            toast("Password modification failed")
                        }
                    }

                    override fun failure(msg: String) {
                       toast(msg)
                    }

                })

            }
        }
    }
}
