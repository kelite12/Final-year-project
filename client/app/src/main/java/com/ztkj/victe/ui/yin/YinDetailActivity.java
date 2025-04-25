package com.ztkj.victe.ui.yin;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.ztkj.victe.MyApplication;
import com.ztkj.victe.R;
import com.ztkj.victe.ui.base.BaseActivity;
import com.ztkj.victe.ui.yin.model.Yin;
import com.bumptech.glide.Glide;
public class YinDetailActivity extends BaseActivity {
        private TextView demo_detail_tv_yinid;
        private TextView demo_detail_tv_title;
        private ImageView demo_detail_iv_pics;
        private TextView demo_detail_tv_re;
        private TextView demo_detail_tv_dan;
        private TextView demo_detail_tv_tang;
        private TextView demo_detail_tv_gai;
        private TextView demo_detail_tv_wei;
        private TextView demo_detail_tv_qian;
        private TextView demo_detail_tv_sendtime;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_detail);
        TongJi();
        getTongjiHeadText().setText("details");
        Yin item = (Yin) getIntent().getSerializableExtra("yin");
        demo_detail_tv_yinid = findViewById(R.id.yin_detail_tv_yinid);
        demo_detail_tv_yinid.setText(item.getYinid()+"");
        demo_detail_tv_title = findViewById(R.id.yin_detail_tv_title);
        demo_detail_tv_title.setText(item.getTitle()+"");
        demo_detail_iv_pics=findViewById(R.id.yin_detail_iv_pics);
        Glide.with(this).load(MyApplication.Companion.getHostFile()+item.getPics()).into(demo_detail_iv_pics);
        demo_detail_tv_re = findViewById(R.id.yin_detail_tv_re);
        demo_detail_tv_re.setText(item.getRe()+"");
        demo_detail_tv_dan = findViewById(R.id.yin_detail_tv_dan);
        demo_detail_tv_dan.setText(item.getDan()+"");
        demo_detail_tv_tang = findViewById(R.id.yin_detail_tv_tang);
        demo_detail_tv_tang.setText(item.getTang()+"");
        demo_detail_tv_gai = findViewById(R.id.yin_detail_tv_gai);
        demo_detail_tv_gai.setText(item.getGai()+"");
        demo_detail_tv_wei = findViewById(R.id.yin_detail_tv_wei);
        demo_detail_tv_wei.setText(item.getWei()+"");
        demo_detail_tv_qian = findViewById(R.id.yin_detail_tv_qian);
        demo_detail_tv_qian.setText(item.getQian()+"");
        demo_detail_tv_sendtime = findViewById(R.id.yin_detail_tv_sendtime);
        demo_detail_tv_sendtime.setText(item.getSendtime()+"");

    }


}