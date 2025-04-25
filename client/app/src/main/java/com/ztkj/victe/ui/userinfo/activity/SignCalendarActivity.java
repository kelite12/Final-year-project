package com.ztkj.victe.ui.userinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victe.msit.retrofitlibrary.utils.RetrofitTools;
import com.ztkj.victe.MyApplication;
import com.ztkj.victe.R;

import com.ztkj.victe.ui.attence.Attence;
import com.ztkj.victe.ui.base.BaseActivity;
import com.ztkj.victe.utils.SignCalendar;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;

public class SignCalendarActivity extends BaseActivity {
    private SignCalendar calendar;
    private String date;
    private TextView btn_sign;
    private TextView tv_sign_year_month;
    List<String> list = new ArrayList<String>();
    private int month;
    private int year;
    private RelativeLayout rlGetGiftData;
    private TextView tvGetSunValue;
    private ImageView ivSun;
    private ImageView ivSunBg;
    private RelativeLayout rlQuedingBtn;
    private RelativeLayout rlBtnSign;
    private ImageView signBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);
        TongJi();
        getTongjiHeadText().setText("My clock in");

        //接收传递过来的初始化数据
//        SignCalendarReq signCalendarReq = (SignCalendarReq) getIntent().getSerializableExtra("userInfos");

        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        date = formatter.format(curDate);
        calendar = (SignCalendar) findViewById(R.id.sc_main);
        btn_sign = (TextView) findViewById(R.id.btn_sign);
        tv_sign_year_month = (TextView) findViewById(R.id.tv_sign_year_month);
        rlGetGiftData = (RelativeLayout) findViewById(R.id.rl_get_gift_view);
        tvGetSunValue = (TextView) findViewById(R.id.tv_text_one);
        ivSun = (ImageView) findViewById(R.id.iv_sun);
        ivSunBg = (ImageView) findViewById(R.id.iv_sun_bg);
        signBack = (ImageView) findViewById(R.id.i8show_attention_back);
        rlQuedingBtn = (RelativeLayout) findViewById(R.id.rl_queding_btn);
        rlBtnSign = (RelativeLayout) findViewById(R.id.rl_btn_sign);

        tv_sign_year_month.setText(year + "年" + (month + 1) + "月");//设置日期


        rlBtnSign.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                data();
            }
        });

        getData();

    }

    void data(){
        ArrayList<MultipartBody.Part> parts = new ArrayList<MultipartBody.Part>();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("userid", MyApplication.user.getUserid()+"");
        RetrofitTools.Companion.upload("insertAttence", map, parts, new RetrofitTools.IRetrofitResponse() {
            @Override
            public <T> void success(T succ) {
                if("true".equals(succ)){
                    toast("Check in successful");
                    btn_sign.setText("Already checked in");
                }else{
                    toast("You have already checked in today");
                }

            }

            @Override
            public void failure(@NotNull String msg) {

            }
        });
    }

    void getData(){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("userid", MyApplication.user.getUserid()+"");
        RetrofitTools.Companion.get("getMyAttence", map, Attence.class, new RetrofitTools.IRetrofitResponse() {
            @Override
            public <T> void success(T succ) {
                List<Attence> attences = (List<Attence>) succ;
                List<String> datas = new ArrayList<String>();
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                if(attences != null && attences.size()>0){
                    for(Attence attence:attences){
                        try {
                            datas.add(formatter2.format(formatter2.parse(attence.getTime())));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
//                    datas.add("2020-03-15");
//                    datas.add("2020-03-13");
                    }
                }

                calendar.addMarks(datas, R.mipmap.qd);
            }

            @Override
            public void failure(@NotNull String msg) {

            }
        });
    }



}
