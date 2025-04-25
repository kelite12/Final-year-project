package com.ztkj.victe.ui.course.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.jph.takephoto.model.TImage
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.TakeActivity
import com.ztkj.victe.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_insert_course.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class InsertCourseActivity : BaseActivity() {

    var parts = ArrayList<MultipartBody.Part>();
    var week = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_course)
        TongJi()
        tongji_head_text.text="添加课程信息"
        insert_course_imageview.setOnClickListener {
            var intent = Intent(applicationContext, TakeActivity::class.java)
            intent.putExtra("takephoto",1)
            startActivityForResult(intent,0x123)
        }
        insert_course_week.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                week = position+1;
            }

        })
        submit_insert_course.setOnClickListener {
            if(TextUtils.isEmpty(insert_course_name.text)){
                toast("课程名称不能为空")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(insert_course_content.text)){
                insert_course_content.setText("")
            }
            if(TextUtils.isEmpty(insert_course_start.text)){
                toast("请输入第几节课开始")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(insert_course_count.text)){
                toast("请输入连续几节课")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(insert_course_classroom.text)){
                toast("请输入教室")
                return@setOnClickListener
            }

            var map = HashMap<String,String>();
            map.put("coursename",insert_course_name.text.toString().trim());
            map.put("content",insert_course_content.text.toString().trim());
            map.put("score","0");
            map.put("sendid",MyApplication.user.userid.toString());
            map.put("section",insert_course_start.text.toString().trim());
            map.put("sectionspan",insert_course_count.text.toString().trim());
            map.put("week",week.toString());
            map.put("courseflag","1");
            map.put("classroom",insert_course_classroom.text.toString().trim());
            map.put("teacherid","0");
            RetrofitTools.upload("insertCourse",map,parts,object :RetrofitTools.IRetrofitResponse{
                override fun <T> success(succ: T) {
                    if("true".equals(succ)){
                        toast("添加成功")
                        finish()
                    }
                    else{
                        toast("添加课程失败")
                    }
                }

                override fun failure(msg: String) {
                    toast("添加课程失败")
                }

            })
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==0x123){
            var timg = data!!.getSerializableExtra("result") as TImage;
            Glide.with(applicationContext).load(timg.originalPath).into(insert_course_imageview);
            var file = File(timg.originalPath);
            var requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
            var body: MultipartBody.Part =
                MultipartBody.Part.createFormData("picutre", file.getName(), requestFile);
            parts.add(body)
        }
    }
}
