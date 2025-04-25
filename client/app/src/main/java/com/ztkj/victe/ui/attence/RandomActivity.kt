package com.ztkj.victe.ui.attence

import android.os.Bundle
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_random.*
import java.util.*

class RandomActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)
        TongJi()
        tongji_head_text.setText("随机选人")
        var cal = Calendar.getInstance();
        var map = HashMap<String,String>();
        map.put("remark1","${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)+1}"+"-${cal.get(Calendar.DAY_OF_MONTH)}")
        RetrofitTools.post("getAllByRemark",map,Attence::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var attences = succ as List<Attence>
                if(attences != null && attences.size>0){
                    var num = Random().nextInt(attences.size)
                    random_tv.setText(attences.get(num).user.username)
                }else{
                    random_tv.setText("暂无学生签到")
                }


            }

            override fun failure(msg: String) {
                toast(msg)
            }

        })
    }
}
