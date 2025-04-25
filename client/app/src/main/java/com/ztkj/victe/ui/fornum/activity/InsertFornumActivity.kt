package com.ztkj.victe.ui.fornum.activity

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TimePicker
import com.jph.takephoto.model.TImage
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.TakeActivity
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.utils.AlarmUtils
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_insert_fornum.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class InsertFornumActivity : BaseActivity() {
    var parts = ArrayList<MultipartBody.Part>()
    var urls:String=""
    var status = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_fornum)
        TongJi()
        status = intent.getIntExtra("status",0);
        if(status==0){
            tongji_head_text.setText("add")
        }else{
            tongji_head_text.setText("Add Sports")
//            selectCamera.visibility = View.VISIBLE
            selectCamera.setText("set alarm")
        }

        selectCamera.setOnClickListener {
            if(status==0){
                var intent = Intent(applicationContext, TakeActivity::class.java)
                intent.putExtra("takephoto", 5)
                startActivityForResult(intent, 0x123)
            }else{
                if(TextUtils.isEmpty(insert_fornum_content.text)){
                    toast("Please enter the content first")
                    return@setOnClickListener
                }
                var cal = Calendar.getInstance()
                TimePickerDialog(this,object :TimePickerDialog.OnTimeSetListener{
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
//                        toast("${hourOfDay}:${minute}")
                        cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                        cal.set(Calendar.MINUTE,minute)
//                        toast("设置成功")
                        selectCamera.setText("${hourOfDay}:${minute}")
                        var intent = Intent(this@InsertFornumActivity, ShowMsgActivity::class.java)
                        var time = System.currentTimeMillis();
                        intent.putExtra("title",insert_fornum_content.text.toString())
                        Log.e("TAG","====${cal.timeInMillis-time}====")
                        AlarmUtils.startAlarmActivity(this@InsertFornumActivity,cal.timeInMillis,intent);

                    }

                },cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
            }

        }
        submit_fornum.setOnClickListener {
            if(TextUtils.isEmpty(fornum_title.text)){
                fornum_title.setText("");
            }
            if (TextUtils.isEmpty(insert_fornum_content.text)){
                insert_fornum_content.setText("")
            }
            getdata(parts,insert_fornum_content.text.toString())
        }
    }

    //提交数据
    fun getdata(parts:List<MultipartBody.Part>,content:String){
        var map = HashMap<String,String>();
        map.put("content",content)
        map.put("title",fornum_title.text.toString())
        map.put("username", MyApplication.user.username!!)
        map.put("userid", MyApplication.user.userid!!.toString())
        map.put("status", status.toString())
        map.put("remark1", selectCamera.text.toString())
        RetrofitTools.upload("insertFornum",map,parts,object:RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("true".equals(succ.toString())){
                    toast("Added successfully")
                    finish();
                }else{
                    toast("Add failed")
                }
            }
            override fun failure(msg: String) {
                toast("network anomaly")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x123) {
            var timgs = data!!.getSerializableExtra("results") as List<TImage>;
            var cameras = ArrayList<String>();

            for (item in timgs) {
                cameras.add(item.originalPath)
                var file = File(item.originalPath);
                var requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
                var body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
                parts.add(body)
                urls = "${urls}#${file.name}"
            }
            insert_fornum_imageview.setList(cameras)
        }

    }
}
