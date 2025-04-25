package com.ztkj.victe.ui.userinfo.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MainActivity
import com.ztkj.victe.MyApplication
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.R
import com.ztkj.victe.ui.userinfo.model.User
import com.ztkj.victe.utils.Md5AndSha1
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Message
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_regeist.setOnClickListener {
            startActivity(Intent(this,RegistActivity::class.java))
        }
        login_login_text.setOnClickListener {
            if(TextUtils.isEmpty(login_username_edittext.text.toString())){
                toast("The username cannot be empty");
                return@setOnClickListener;
            }
            if(TextUtils.isEmpty(login_password_edittext.text.toString())){
                toast("Password cannot be empty")
                return@setOnClickListener;
            }
            var map = HashMap<String,String>();
            map.put("username",login_username_edittext.text.toString().trim());
            map.put("password",login_password_edittext.text.toString().trim());
            //提交登录
            RetrofitTools.post("login",map,User::class.java,object :RetrofitTools.IRetrofitResponse{
                override fun <T> success(succ: T) {
                   if(TextUtils.isEmpty(succ.toString())){
                       toast("Incorrect username or password");
                       return;
                   }else{
                       //请求成功
                       var user = succ as User;
                       if(user.userid>0){
                           //登陆成功
                           toast("Login succeeded")
                           MyApplication.user = user;
//   startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                           getToken()
//                          finish();
                       }
                       else{
                           //失败
                           toast("Incorrect username or password");
                       }
                   }

                }

                override fun failure(msg: String) {
                   //请求失败
                    toast(msg);
                }

            })

        }

    }

    /*************即时通讯*****************/
    //获取token
    fun getToken(){
        var headmap = HashMap<String,String>();
        headmap.put("App-Key","82hegw5uh0g8x")
        headmap.put("Nonce",System.currentTimeMillis().toString())
        headmap.put("Timestamp",System.currentTimeMillis().toString())
        headmap.put("Signature", Md5AndSha1.sha1("jsrDitubSO6Qh${headmap.get("Nonce")}${headmap.get("Timestamp")}"))
        var map = HashMap<String,String>();
        map.put("userId",MyApplication.user.username!!)
        map.put("name", MyApplication.user.username!!)
        RetrofitTools.get("http://api.cn.ronghub.com/user/getToken.json",headmap,map,String::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
               var json = JSONObject(succ.toString());
                var token = json.getString("token");
                connect(token);
            }

            override fun failure(msg: String) {
                toast(msg);
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        },false);
    }
    //连接融云
    private fun connect(token: String) {
//        toast(token);
        RongIMClient.connect(token, object : RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            override fun onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            override fun onSuccess(userid: String) {
//                toast("conn${userid}")
                joinGroup()
                setListeren()
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            override fun onError(errorCode: RongIMClient.ErrorCode) {
                toast("连接失败"+errorCode.message+":"+errorCode.value)
            }
        })

    }

    //设置监听
    fun setListeren(){
        RongIM.getInstance().setMessageInterceptor(object : RongIM.MessageInterceptor{
            override fun intercept(p0: Message?): Boolean {
                Log.e("TAG",p0.toString())
                Log.e("TAG",p0!!.targetId.toString())
                MyApplication.messages.add(p0!!.targetId.toString())
                return false;
            }

        })
    }
    //加入群组
    fun joinGroup(){
        var headmap = HashMap<String,String>();
        headmap.put("App-Key","82hegw5uh0g8x")
        headmap.put("Nonce","644901492")
        headmap.put("Timestamp","1582030431")
        headmap.put("Signature","f6743ff3977a576896149300f314405d2bd6bbe9")
        var map = HashMap<String,String>();
        map.put("userId",MyApplication.user.username.toString())
        map.put("groupId", "1")
        map.put("groupName", "家长交流区")
        RetrofitTools.get("http://api.cn.ronghub.com/group/join.json",headmap,map,String::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {

            }

            override fun failure(msg: String) {
            }

        },false)
//        service.joinGroup(headmap,map).enqueue(object : Callback<String> {
//            override fun onFailure(call: Call<String>?, t: Throwable?) {
//                toast("网络异常${call.toString()}----${t.toString()}")
//            }
//            override fun onResponse(call: Call<String>?, response: Response<String>?) {
//                var token = response!!.body()
//                toast("${token}")
////                toast("${token!!.userId}---${token.token}---${token.code}")
////                connect(token!!.token)
//            }
//
//        })
    }
}
