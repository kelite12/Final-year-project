package com.ztkj.victe.ui.userinfo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.jph.takephoto.model.TImage
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.R
import com.ztkj.victe.TakeActivity
import com.ztkj.victe.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_regist.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegistActivity : BaseActivity() {
    var map = HashMap<String,String>();
    var parts = ArrayList<MultipartBody.Part>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        regeist_regeist_text.setOnClickListener {
            if(TextUtils.isEmpty(regeist_username_edittext.text.toString())){
                toast("用户名不能为空")
                return@setOnClickListener;
            }
            if(TextUtils.isEmpty(regeist_password_edittext.text.toString())){
                toast("密码不能为空")
                return@setOnClickListener;
            }
            if (TextUtils.isEmpty(regeist_repassword_edittext.text.toString())){
                toast("确定密码不能为空")
                return@setOnClickListener;
            }
            if(!regeist_password_edittext.text.toString().equals(regeist_repassword_edittext.text.toString())){
                toast("两次密码输入不一致")
                return@setOnClickListener
            }
            map.put("username",regeist_username_edittext.text.toString().trim());
            map.put("password",regeist_password_edittext.text.toString().trim());
            map.put("nickname",regeist_nickname_edittext.text.toString().trim());
            map.put("sex",regeist_sex_edittext.text.toString().trim());
            map.put("age",regeist_age_edittext.text.toString().trim());
            map.put("tel",regeist_tel_edittext.text.toString().trim());
            map.put("address",regeist_address_edittext.text.toString().trim());
            map.put("height",regeist_height_edittext.text.toString().trim());
            map.put("weight",regeist_weight_edittext.text.toString().trim());
            RetrofitTools.upload("insertUser",map,parts,object :RetrofitTools.IRetrofitResponse{
                override fun <T> success(succ: T) {
                    if ("true".equals(succ.toString())){
                        toast("注册成功")
                        finish()
                    }
                    else{
                        toast("注册失败")
                    }
                }

                override fun failure(msg: String) {
                    toast(msg);
                }

            })

        }

        regeistImageView.setOnClickListener {
            var intent = Intent(applicationContext, TakeActivity::class.java)
            intent.putExtra("takephoto",1)
            startActivityForResult(intent,0x123)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==0x123){
            var timg = data!!.getSerializableExtra("result") as TImage;
            Glide.with(applicationContext).load(timg.originalPath).into(regeistImageView);
            var file = File(timg.originalPath);
            var requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
            var body: MultipartBody.Part =
                MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
            parts.add(body)
        }
    }
}
