package com.ztkj.victe.ui.xiu;

import android.app.TimePickerDialog;
import android.os.Environment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import com.jph.takephoto.model.TImage;

import com.victe.msit.retrofitlibrary.utils.RetrofitTools;
import com.ztkj.victe.MyApplication;
import com.ztkj.victe.R;
import com.ztkj.victe.ui.base.BaseActivity;
import com.ztkj.victe.ui.xiu.model.Xiu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import java.io.File;

public class AddXiuActivity extends BaseActivity {
    private Button add_xiu_btn;
    
//        private EditText add_demo_edit_xiuid;
    
        private TextView add_demo_edit_title;
    
        private TextView add_demo_edit_stime;
    
        private TextView add_demo_edit_etime;
    
//        private EditText add_demo_edit_userid;
    
        private EditText add_demo_edit_remark1;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_add);
        TongJi();
        getTongjiHeadText().setText("添加页面");
        add_xiu_btn = findViewById(R.id.add_xiu_btn);
        
//            add_demo_edit_xiuid = findViewById(R.id.add_xiu_edit_xiuid);
        
            add_demo_edit_title = findViewById(R.id.add_xiu_edit_title);
        
            add_demo_edit_stime = findViewById(R.id.add_xiu_edit_stime);
        
            add_demo_edit_etime = findViewById(R.id.add_xiu_edit_etime);
        
//            add_demo_edit_userid = findViewById(R.id.add_xiu_edit_userid);
        
            add_demo_edit_remark1 = findViewById(R.id.add_xiu_edit_remark1);

        add_demo_edit_stime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddXiuActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        add_demo_edit_stime.setText(i+":"+i1);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true).show();

            }
        });

        add_demo_edit_etime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddXiuActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        add_demo_edit_etime.setText(i+":"+i1);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true).show();
            }
        });
        

        add_xiu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
            }
        });

    }
    List<MultipartBody.Part> parts = new ArrayList<>();
    private void getdata(){
        HashMap<String,String> map = new HashMap<>();
    
//         map.put("xiuid",add_demo_edit_xiuid.getText().toString().trim());
    
         map.put("title",add_demo_edit_title.getText().toString().trim());
    
         map.put("stime",add_demo_edit_stime.getText().toString().trim());
    
         map.put("etime",add_demo_edit_etime.getText().toString().trim());
    
         map.put("userid", MyApplication.user.getUserid()+"");
    
         map.put("remark1",add_demo_edit_remark1.getText().toString().trim());
    

        RetrofitTools.Companion.upload("insertXiu",map, parts,new RetrofitTools.IRetrofitResponse(){
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