package com.ztkj.victe.ui.userinfo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.mylhyl.circledialog.CircleDialog
import com.mylhyl.circledialog.view.listener.OnInputClickListener
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_show_person.*
import okhttp3.MultipartBody

class ShowPersonActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TongJi()
        tongji_head_text.setText("Personal Information")
        setContentView(R.layout.activity_show_person)
        show_person_username_tv.setText(MyApplication.user.username)
        show_person_nickname_tv.setText(MyApplication.user.nickname)
        show_person_age_tv.setText(MyApplication.user.age.toString())
        show_person_tel_tv.setText(MyApplication.user.tel)
        show_person_address_tv.setText(MyApplication.user.address)
        show_person_sex_tv.setText(MyApplication.user.sex)
        show_person_weight_tv.setText(MyApplication.user.weight.toString())
        show_person_height_tv.setText(MyApplication.user.height.toString())

        show_person_weight_tv.setOnClickListener {
            CircleDialog.Builder(this@ShowPersonActivity)
                .setTitle("weight")
                .setInputHint("Please enter your weight")
                .setPositiveInput("ok",object: OnInputClickListener {
                    override fun onClick(text: String?, v: View?) {
                        if (TextUtils.isEmpty(text)){
                            toast("The content cannot be empty")
                            return;
                        }
                        getdata("weight",text!!)
                        MyApplication.user.nickname = text;
                        show_person_nickname_tv.setText(text!!)
                    }
                })
                .setNegative("cancel", null)
                .show()
        }

        show_person_nickname_tv.setOnClickListener {
            CircleDialog.Builder(this@ShowPersonActivity)
                .setTitle("nickname")
                .setInputHint("Please enter a nickname")
                .setPositiveInput("ok",object: OnInputClickListener {
                    override fun onClick(text: String?, v: View?) {
                        if (TextUtils.isEmpty(text)){
                            toast("The content cannot be empty")
                            return;
                        }
                        getdata("nickname",text!!)
                        MyApplication.user.nickname = text;
                        show_person_nickname_tv.setText(text!!)
                    }
                })
                .setNegative("cancel", null)
                .show()
        }
        show_person_address_tv.setOnClickListener {
            CircleDialog.Builder(this@ShowPersonActivity)
                .setTitle("address")
                .setInputHint("Please enter the address")
                .setPositiveInput("ok",object: OnInputClickListener {
                    override fun onClick(text: String?, v: View?) {
                        if (TextUtils.isEmpty(text)){
                            toast("The content cannot be empty")
                            return;
                        }
                        getdata("address",text!!)
                        MyApplication.user.address = text;
                        show_person_address_tv.setText(text!!)
                    }
                })
                .setNegative("cancel", null)
                .show()
        }
        show_person_tel_tv.setOnClickListener {
            CircleDialog.Builder(this@ShowPersonActivity)
                .setTitle("phone")
                .setInputHint("Please enter phone number")
                .setPositiveInput("ok",object: OnInputClickListener {
                    override fun onClick(text: String?, v: View?) {
                        if (TextUtils.isEmpty(text)){
                            toast("The content cannot be empty")
                            return;
                        }
                        getdata("address",text!!)
                        MyApplication.user.tel = text;
                        show_person_tel_tv.setText(text!!)
                    }
                })
                .setNegative("cancel", null)
                .show()
        }
        show_person_age_tv.setOnClickListener {
            CircleDialog.Builder(this@ShowPersonActivity)
                .setTitle("age")
                .setInputHint("Please enter age")
                .setPositiveInput("ok",object: OnInputClickListener {
                    override fun onClick(text: String?, v: View?) {
                        if (TextUtils.isEmpty(text)){
                            toast("The content cannot be empty")
                            return;
                        }
                        getdata("age",text!!)
                        MyApplication.user.age = text.toInt();
                        show_person_age_tv.setText(text!!)
                    }
                })
                .setNegative("cancel", null)
                .show()
        }

        if(MyApplication.sumStep<500){
            Glide.with(this).load(R.mipmap.x1).error(R.mipmap.ic_launcher).into(show_person_xunz_tv)
            xunz_tv.setText("bronze")
        }else if(MyApplication.sumStep<1000){
            Glide.with(this).load(R.mipmap.x2).error(R.mipmap.ic_launcher).into(show_person_xunz_tv)
            xunz_tv.setText("silver")
        }else{
            Glide.with(this).load(R.mipmap.x3).error(R.mipmap.ic_launcher).into(show_person_xunz_tv)
            xunz_tv.setText("diamond")
        }

        var bmi = MyApplication.user.weight/MyApplication.user.height
        if("man".equals(MyApplication.user.sex)){
            if(bmi>=18&&bmi<=24){
                show_person_bmi_tv.setText("normal")
            }else if (bmi<18){
                show_person_bmi_tv.setText("malnutrition")
            }else if(bmi>24&&bmi<=28){
                show_person_bmi_tv.setText("overweight")
            }else{
                show_person_bmi_tv.setText("Obesity")
            }
        }else{
            if(bmi>=17.2&&bmi<=24){
                show_person_bmi_tv.setText("normal")
            }else if (bmi<17.2){
                show_person_bmi_tv.setText("malnutrition")
            }else if(bmi>24&&bmi<=27.9){
                show_person_bmi_tv.setText("overweight")
            }else{
                show_person_bmi_tv.setText("Obesity")
            }
        }

    }

    fun getdata(key:String,value:String){
        var parts = ArrayList<MultipartBody.Part>()
        var map = HashMap<String,String>()
        map.put("userid",MyApplication.user.userid.toString())
        map.put(key,value)
        RetrofitTools.upload("updateUser",map,parts,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("true".equals(succ)){
                    toast("Modified successfully")
                }else{
                    toast("Modification failed")
                }
            }

            override fun failure(msg: String) {
              toast(msg)
            }

        })
    }
}
