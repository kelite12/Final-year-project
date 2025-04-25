package com.victe.vproject.modules.weather

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mylhyl.circledialog.CircleDialog
import com.mylhyl.circledialog.view.listener.OnInputClickListener
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.news.model.News
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.frag_weather.*
import kotlinx.android.synthetic.main.weather_recy_item.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by 13526 on 2018/3/21.
 */
class WeatherFragment: LazyFragment(){
    companion object {
        fun newInstance(isLazyLoad: Boolean): WeatherFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = WeatherFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onFragmentStartLazy() {

    }

    override fun onFragmentStopLazy() {

    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_weather)


    }

    override fun onResumeLazy() {
        var formate = SimpleDateFormat("yyyy年MM月dd日")
        weather_today.setText(formate.format(Date()));
        getdataWeather();
        weather_city.setOnLongClickListener {
            CircleDialog.Builder(activity!!)
                    .setTitle("提示")
                    .setInputHint("请输入城市名")
                    .setPositiveInput("确定",object: OnInputClickListener {
                        override fun onClick(text: String?, v: View?) {
                            if (TextUtils.isEmpty(text)){
                                toast("城市名不能为空")
                            }
                            else{
                                activity!!.getSharedPreferences("weather", Context.MODE_PRIVATE).edit().putString("city",text).commit()
                                getdataWeather();
                            }

                        }
                    } )
                    .setNegative("取消",null)
                    .show()
            true
        }
    }

    override fun onDestroyViewLazy() {

    }

    fun getdataWeather() {
        var map = HashMap<String, String>();
        var city = activity!!.getSharedPreferences("weather", Context.MODE_PRIVATE).getString("city", "南昌");
        map.put("cityname",city)
        map.put("dtype", "1")
        map.put("format", "1")
        map.put("key", "f94b5e79879c8143c5499007a3a5dc15")
        RetrofitTools.get("http://v.juhe.cn/weather/index",map,String::class.java,object:RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
               // Log.e("TAG",succ.toString());
                try {
                    var weather = Gson().fromJson(JSONObject(succ.toString()).getJSONObject("result").getJSONObject("today").toString(), Weather::class.java)
                    weather_city.text = weather.city
                    weather_windy.text = weather.weather
                    weather_temperature.text = weather.temperature
                    weather_advice.text = weather.dressing_advice
                    // Log.e("TAG",weather_windy.text.toString())
                    if ("晴".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_01)
                    } else if ("多云".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_03)
                    } else if ("阴".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_04)
                    } else if ("浮尘".equals(weather_windy.text.toString()) || "雾".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_05)
                    } else if ("沙尘暴".equals(weather_windy.text.toString()) || "霾".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_06)
                    } else if ("雷阵雨".equals(weather_windy.text.toString()) || "阵雨".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_07)
                    } else if ("小雨".equals(weather_windy.text.toString()) || "冻雨".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_08)
                    } else if ("大雨".equals(weather_windy.text.toString()) || "暴雨".equals(weather_windy.text.toString()) ||
                        "大暴雨".equals(weather_windy.text.toString()) || "特大暴雨".equals(weather_windy.text.toString()) ||
                        "中雨-大雨".equals(weather_windy.text.toString()) || "大雨-暴雨".equals(weather_windy.text.toString()) ||
                        "暴雨-大暴雨".equals(weather_windy.text.toString()) || "大暴雨-特大暴雨".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_09)
                    } else if ("小雨-中雨".equals(weather_windy.text.toString()) || "中雨".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_10)
                    } else if ("阵雪".equals(weather_windy.text.toString()) || "小雪".equals(weather_windy.text.toString()) ||
                        "中雪".equals(weather_windy.text.toString()) || "小雪-中雪".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_11)
                    } else if ("大雪".equals(weather_windy.text.toString()) || "暴雪".equals(weather_windy.text.toString()) ||
                        "中雪-大雪".equals(weather_windy.text.toString()) || "大雪-暴雪".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_12)
                    } else if ("雨夹雪".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_13)
                    } else if ("雷阵雨伴有冰雹".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_14)
                    } else if ("扬沙".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_17)
                    } else if ("强沙尘暴".equals(weather_windy.text.toString())) {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_18)
                    } else {
                        weather_pic.setBackgroundResource(R.drawable.weathericon_appwidget_15)
                    }
                    var jsonObject = JSONObject(succ.toString()).getJSONObject("result").getJSONObject("future")
                    var weathers = ArrayList<Weather>();
                    for(item in jsonObject.keys()){
                        weathers.add(Gson().fromJson(jsonObject[item].toString(),Weather::class.java))
                    }
                    weather_recy.layoutManager = LinearLayoutManager(applicationContext)
                    weather_recy.adapter = MyAdapter(weathers);

                } catch (e: Exception) {
                    activity!!.getSharedPreferences("weather", Context.MODE_PRIVATE).edit().putString("city", "南昌").commit()
                    getdataWeather();
                }
            }

            override fun failure(msg: String) {
                toast(msg)
            }

        },false)



    }

    inner class MyAdapter(mlist: List<Weather>) : BaseAdapterRecyclerView<Weather>(mlist, R.layout.weather_recy_item) {
        override fun convert(holder: RecyclerHolder, item: Weather, position: Int, isScrolling: Boolean) {
            with(holder.itemView) {
                weather_recy_item_week.text = item.week
                weather_recy_item_weather.text = item.weather
                weather_recy_item_temperature.text = item.temperature
            }
        }

    }
}