package com.ztkj.victe.ui.xiu;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ztkj.victe.R;
import com.ztkj.victe.ui.base.BaseActivity;
import com.ztkj.victe.ui.xiu.model.Xiu;

public class XiuDetailActivity extends BaseActivity {

    
        private TextView demo_detail_tv_xiuid;
    
        private TextView demo_detail_tv_title;
    
        private TextView demo_detail_tv_stime;
    
        private TextView demo_detail_tv_etime;
    
        private TextView demo_detail_tv_userid;
    
        private TextView demo_detail_tv_remark1;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_detail);
        TongJi();
        getTongjiHeadText().setText("详情");
        Xiu item = (Xiu) getIntent().getSerializableExtra("xiu");
        
            demo_detail_tv_xiuid = findViewById(R.id.xiu_detail_tv_xiuid);
            demo_detail_tv_xiuid.setText(item.getXiuid()+"");
        
            demo_detail_tv_title = findViewById(R.id.xiu_detail_tv_title);
            demo_detail_tv_title.setText(item.getTitle()+"");
        
            demo_detail_tv_stime = findViewById(R.id.xiu_detail_tv_stime);
            demo_detail_tv_stime.setText(item.getStime()+"");
        
            demo_detail_tv_etime = findViewById(R.id.xiu_detail_tv_etime);
            demo_detail_tv_etime.setText(item.getEtime()+"");
        
            demo_detail_tv_userid = findViewById(R.id.xiu_detail_tv_userid);
            demo_detail_tv_userid.setText(item.getUserid()+"");
        
            demo_detail_tv_remark1 = findViewById(R.id.xiu_detail_tv_remark1);
            demo_detail_tv_remark1.setText(item.getRemark1()+"");
        

    }


}