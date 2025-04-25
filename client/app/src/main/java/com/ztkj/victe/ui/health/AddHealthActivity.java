package com.ztkj.victe.ui.health;

import android.os.Environment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import com.jph.takephoto.model.TImage;

import com.victe.msit.retrofitlibrary.utils.RetrofitTools;
import com.ztkj.victe.MyApplication;
import com.ztkj.victe.R;
import com.ztkj.victe.ui.base.BaseActivity;
import com.ztkj.victe.ui.health.model.Health;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import java.io.File;

public class AddHealthActivity extends BaseActivity {
    private Button add_health_btn;
    
//        private EditText add_demo_edit_healthid;
    
        private EditText add_demo_edit_age;
    
//        private EditText add_demo_edit_sex;
    
        private EditText add_demo_edit_xuey;
    
        private EditText add_demo_edit_xuet;
    
        private EditText add_demo_edit_xuez;
    
        private EditText add_demo_edit_xueg;
    
//        private EditText add_demo_edit_userid;
    
        private EditText add_demo_edit_remark1;
        private RadioGroup rg;

        private String sex="男";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_add);
        TongJi();
        getTongjiHeadText().setText("添加页面");
        add_health_btn = findViewById(R.id.add_health_btn);
        

        
            add_demo_edit_age = findViewById(R.id.add_health_edit_age);
            rg = findViewById(R.id.rg);


        
            add_demo_edit_xuey = findViewById(R.id.add_health_edit_xuey);
        
            add_demo_edit_xuet = findViewById(R.id.add_health_edit_xuet);
        
            add_demo_edit_xuez = findViewById(R.id.add_health_edit_xuez);
        
            add_demo_edit_xueg = findViewById(R.id.add_health_edit_xueg);
        
            add_demo_edit_remark1 = findViewById(R.id.add_health_edit_remark1);
        

        add_health_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rb1){
                    sex="男";
                }else {
                    sex="女";
                }
            }
        });

    }
    List<MultipartBody.Part> parts = new ArrayList<>();
    private void getdata(){
        HashMap<String,String> map = new HashMap<>();

         map.put("age",add_demo_edit_age.getText().toString().trim());
    
         map.put("sex",sex);
    
         map.put("xuey",add_demo_edit_xuey.getText().toString().trim());
    
         map.put("xuet",add_demo_edit_xuet.getText().toString().trim());
    
         map.put("xuez",add_demo_edit_xuez.getText().toString().trim());
    
         map.put("xueg",add_demo_edit_xueg.getText().toString().trim());
    
         map.put("userid", MyApplication.user.getUserid()+"");
    
         map.put("remark1",add_demo_edit_remark1.getText().toString().trim());
    

        RetrofitTools.Companion.upload("insertHealth",map, parts,new RetrofitTools.IRetrofitResponse(){
            public void success(Object succ){
                if("true".equals(succ)){
                    toast("添加成功");
                    finish();
                }
            }
            public void failure(String succ){

            }
        });
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == 0x123) {
            //相册
            List<TImage> timgs = (List<TImage>) data.getSerializableExtra("results");
            ArrayList<String> cameras =  new ArrayList<String>();
            for (TImage item:timgs) {
                cameras.add(item.getOriginalPath());
                File file = new File(item.getOriginalPath());
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
                parts.add(body);
//                urls = urls+"#"+file.getName();
            }
            //显示图片
        }else if (requestCode==0x124){
            //相机
            List<TImage> timgs = (List<TImage>) data.getSerializableExtra("results");
            ArrayList<String> cameras =  new ArrayList<String>();
            for (TImage item:timgs) {
                String filepath = Environment.getExternalStorageDirectory().getAbsolutePath()+item.getOriginalPath().replace("name","DCIM");
                File file = new File(filepath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
                parts.add(body);
//                urls = urls+"#"+file.getName();
            }
        }
    }
}