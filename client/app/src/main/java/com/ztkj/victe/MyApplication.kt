package com.ztkj.victe

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.uuzuche.lib_zxing.activity.ZXingLibrary
//import com.baidu.mapapi.CoordType
//import com.baidu.mapapi.SDKInitializer
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.ui.userinfo.model.User
import io.rong.imkit.RongIM
import java.io.Serializable

class MyApplication:Application(){
    companion object {
        var host = "http://10.126.6.21/";
        var hostFile = host+"file/";
        lateinit var user:User;
        var goodsMap = HashMap<String,Serializable>();
        var sumStep = 0
        var messages = ArrayList<String>()
    }
    override fun onCreate() {
        super.onCreate()
//        SDKInitializer.initialize(this)
//        SDKInitializer.setCoordType(CoordType.BD09LL)
        RetrofitTools.init(host,"a202508");
        RongIM.init(this)
        SDKInitializer.initialize(this)
        SDKInitializer.setCoordType(CoordType.BD09LL)

        ZXingLibrary.initDisplayOpinion(this);

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}