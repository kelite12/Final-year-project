package com.ztkj.victe.ui.health;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ztkj.victe.R;
import com.ztkj.victe.ui.base.BaseActivity;
import com.ztkj.victe.ui.health.model.Health;

public class HealthDetailActivity extends BaseActivity {

    
        private TextView demo_detail_tv_healthid;
    
        private TextView demo_detail_tv_age;
    
        private TextView demo_detail_tv_sex;
    
        private TextView demo_detail_tv_xuey;
    
        private TextView demo_detail_tv_xuet;
    
        private TextView demo_detail_tv_xuez;
    
        private TextView demo_detail_tv_xueg;
    
        private TextView demo_detail_tv_userid;
    
        private TextView demo_detail_tv_remark1;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_detail);
        TongJi();
        getTongjiHeadText().setText("详情");
        Health item = (Health) getIntent().getSerializableExtra("health");
        
            demo_detail_tv_healthid = findViewById(R.id.health_detail_tv_healthid);
            demo_detail_tv_healthid.setText(item.getHealthid()+"");
        
            demo_detail_tv_age = findViewById(R.id.health_detail_tv_age);
            demo_detail_tv_age.setText(item.getAge()+"");
        
            demo_detail_tv_sex = findViewById(R.id.health_detail_tv_sex);
            demo_detail_tv_sex.setText(item.getSex()+"");
        
            demo_detail_tv_xuey = findViewById(R.id.health_detail_tv_xuey);
            demo_detail_tv_xuey.setText(item.getXuey()+"");
        
            demo_detail_tv_xuet = findViewById(R.id.health_detail_tv_xuet);
            demo_detail_tv_xuet.setText(item.getXuet()+"");
        
            demo_detail_tv_xuez = findViewById(R.id.health_detail_tv_xuez);
            demo_detail_tv_xuez.setText(item.getXuez()+"");
        
            demo_detail_tv_xueg = findViewById(R.id.health_detail_tv_xueg);
            demo_detail_tv_xueg.setText(item.getXueg()+"");
        
            demo_detail_tv_userid = findViewById(R.id.health_detail_tv_userid);
            demo_detail_tv_userid.setText(item.getUserid()+"");
        
            demo_detail_tv_remark1 = findViewById(R.id.health_detail_tv_remark1);
            demo_detail_tv_remark1.setText(item.getRemark1()+"");
        

    }


}