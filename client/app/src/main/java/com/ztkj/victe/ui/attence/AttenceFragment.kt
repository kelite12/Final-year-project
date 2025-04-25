package com.victe.vproject.modules.attence

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Message
import android.util.Log
import com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE
import com.jph.takephoto.model.TImage
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.TakeActivity
import com.ztkj.victe.ui.BDToken
import com.ztkj.victe.ui.attence.InsertAskActivity
import com.ztkj.victe.ui.attence.ShowAttenceActivity
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.course.model.Course
import com.ztkj.victe.utils.Base64Util2
import com.ztkj.victe.utils.Dish
import com.ztkj.victe.utils.FileUtil
import kotlinx.android.synthetic.main.frag_attence.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*


class AttenceFragment : LazyFragment() {
    var flag = true;
    private var courses = ArrayList<Course>();
    companion object {
        fun newInstance(isLazyLoad: Boolean): AttenceFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = AttenceFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onFragmentStartLazy() {
    }

    override fun onFragmentStopLazy() {
    }
    fun getToken() {
        var map = HashMap<String, String>();
        map.put("grant_type", "client_credentials");
        map.put("client_id", "TRBzVN5tM3ynBZB5f8NIHuTd");
        map.put("client_secret", "07ukuY85Qs2YtcFkzuzUvpH7uCmi9UHo");
        RetrofitTools.post("https://aip.baidubce.com/oauth/2.0/token", map,
            BDToken::class.java, object :
                RetrofitTools.IRetrofitResponse {
                override fun <T> success(succ: T) {
                    var tokenobj = succ as BDToken;
//                toast(token.toString())

                    token = tokenobj.access_token;
                    Log.e("TAG", "${token}")
                    facecheck();

                }

                override fun failure(msg: String) {
                    toast(msg)
                }

            })
    }

    //人脸查询
    fun facecheck() {
        Thread() {
            kotlin.run {
                activity!!.runOnUiThread {
                    toast("正在识别，请稍后")
                }
                Log.e("TAG", "正在网络请求....")
                var result = Dish.faceSearch(imgPath, token)
                Log.e("TAG", result)
                activity!!.runOnUiThread {
                    try{
                        var json = JSONObject(result).getJSONObject("result")
                        var array = json.getJSONArray("user_list")
                        if(array!=null && array.length()>0){
                            toast("欢迎回家:${array.getJSONObject(0).getString("user_info")}")
                            insertAttence()
                        }else{
                            toast("失败")
                        }
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                        toast("查询失败")
                        Log.e("TAG", "${e.message}")
                    }
                }


            }
        }.start()
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_attence);
        attence_date.text = SimpleDateFormat("yyyy年MM月dd日").format(Date(System.currentTimeMillis()))
        MyThread().start();
        var map = HashMap<String, String>();
        map.put("userid", MyApplication.user.userid.toString())
        ask_tv.setOnClickListener {
            startActivity(Intent(activity,InsertAskActivity::class.java))
        }

        show_attence_tv.setOnClickListener {
            startActivity(Intent(activity,ShowAttenceActivity::class.java))
        }
        attence_tv.setOnClickListener {
            //二维码
//            val intent = Intent(activity, CaptureActivity::class.java)
//            startActivityForResult(intent, REQUEST_CODE)
                            var intent = Intent(applicationContext, TakeActivity::class.java)
                            intent.putExtra("takephoto", 2)
                            startActivityForResult(intent, 0x124)
//            RetrofitTools.get("insertAttence",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
//                override fun <T> success(succ: T) {
//                    if("-1".equals(succ.toString())){
//                        toast("今日已签到过了")
//                        attence_tv.text = "已签到";
//                    }else if("true".equals(succ.toString())){
//                        toast("签到成功")
//                        attence_tv.text = "已签到";
//
//                    }else{
//                        toast("签到失败")
//                    }
//
//                }
//
//                override fun failure(msg: String) {
//                }
//
//            },false)
        }

    }

    override fun onResumeLazy() {
//        getdata();
    }

    override fun onDestroyViewLazy() {
        flag = false

    }
    var imgParam: String = "";
    var imgPath: String = "";
    var token: String = "";
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                var bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    var result = bundle.getString(CodeUtils.RESULT_STRING);
                    resultCodeStr = result;

//                    GetMyaddress(object :GetMyaddress.AddressListeren{
//                        override fun getData(taddress: String) {
//                            address = taddress
//                            var intent = Intent(applicationContext, TakeActivity::class.java)
//                            intent.putExtra("takephoto", 2)
//                            startActivityForResult(intent, 0x124)
//                        }
//
//                    }).getAddress(activity!!)
//                    toast(result)

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    toast("解析二维码失败")
                }
            }
        }
        else if(requestCode==0x124){
            var timgs = data!!.getSerializableExtra("results") as List<TImage>;
            var cameras = ArrayList<String>();
            for (item in timgs) {
                cameras.add(item.originalPath)
                var filepath = Environment.getExternalStorageDirectory().absolutePath+item.originalPath.replace("name","DCIM");
                Log.e("TAG",filepath)
                var file = File(filepath);
                var requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
                var body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
                parts.add(body)
                urls = "${urls}#${file.name}"
                var imgData = FileUtil.readFileByBytes(filepath);
                var imgStr = Base64Util2.encode(imgData);
                imgPath = filepath;
                imgParam = URLEncoder.encode(imgStr, "UTF-8")
                getToken();

            }
//            insertAttence()
        }
    }
    var address="";
    var resultCodeStr="";
    var parts = ArrayList<MultipartBody.Part>()
    var urls:String=""
    fun insertAttence(){
        var map = HashMap<String,String>();
        map.put("userid", MyApplication.user.userid.toString())
        map.put("remark1",resultCodeStr)
        map.put("address",address)
        RetrofitTools.upload("insertAttence",map,parts,object:RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("-1".equals(succ.toString())){
                    toast("今日已签到过了")
                    attence_tv.text = "已签到";
                }else if("true".equals(succ.toString())){
                    toast("签到成功")
                    attence_tv.text = "已签到";

                }else{
                    toast("签到失败")
                }

            }

            override fun failure(msg: String) {
            }

        })
    }

    inner class MyThread : Thread() {
        override fun run() {
            super.run()
            do {
                try {

                    val msg = Message()
                    msg.what = 1
                    activity!!.runOnUiThread {
                        attence_time.setText(SimpleDateFormat("HH:mm:ss").format(Date(System.currentTimeMillis())));
                    }
                    Thread.sleep(1000)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            } while (flag)

        }
    }




}