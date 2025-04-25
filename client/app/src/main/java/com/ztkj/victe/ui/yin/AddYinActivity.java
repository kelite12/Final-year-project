package com.ztkj.victe.ui.yin;

import android.os.Environment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.jph.takephoto.model.TImage;
import android.graphics.BitmapFactory;
import com.victe.msit.retrofitlibrary.utils.RetrofitTools;
import com.ztkj.victe.R;
import com.ztkj.victe.TakeActivity;
import com.ztkj.victe.ui.base.BaseActivity;
import com.ztkj.victe.ui.yin.model.Yin;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import java.io.File;

public class AddYinActivity extends BaseActivity {
    private Button add_yin_btn;
    private EditText add_demo_edit_yinid;
    private EditText add_demo_edit_title;
    private ImageView add_demo_iv_pics;
    private EditText add_demo_edit_re;
    private EditText add_demo_edit_dan;
    private EditText add_demo_edit_tang;
    private EditText add_demo_edit_gai;
    private EditText add_demo_edit_wei;
    private EditText add_demo_edit_qian;
    private EditText add_demo_edit_sendtime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_add);
        TongJi();
        getTongjiHeadText().setText("add page");
        add_yin_btn = findViewById(R.id.add_yin_btn);
            
            
            add_demo_edit_title = findViewById(R.id.add_yin_edit_title);
            
            
            add_demo_iv_pics = findViewById(R.id.add_yin_iv_pics);
            add_demo_iv_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddYinActivity.this, TakeActivity.class);
                intent.putExtra("takephoto", 1);
                startActivityForResult(intent, 0x123);
            }
        });
            
            add_demo_edit_re = findViewById(R.id.add_yin_edit_re);
            
            
            add_demo_edit_dan = findViewById(R.id.add_yin_edit_dan);
            
            
            add_demo_edit_tang = findViewById(R.id.add_yin_edit_tang);
            
            
            add_demo_edit_gai = findViewById(R.id.add_yin_edit_gai);
            
            
            add_demo_edit_wei = findViewById(R.id.add_yin_edit_wei);
            
            
            add_demo_edit_qian = findViewById(R.id.add_yin_edit_qian);
            
            
            add_demo_edit_sendtime = findViewById(R.id.add_yin_edit_sendtime);
            

        add_yin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
            }
        });

    }
    List<MultipartBody.Part> parts = new ArrayList<>();
    private void getdata(){
        HashMap<String,String> map = new HashMap<>();
         map.put("title",add_demo_edit_title.getText().toString().trim());
         map.put("re",add_demo_edit_re.getText().toString().trim());
         map.put("dan",add_demo_edit_dan.getText().toString().trim());
         map.put("tang",add_demo_edit_tang.getText().toString().trim());
         map.put("gai",add_demo_edit_gai.getText().toString().trim());
         map.put("wei",add_demo_edit_wei.getText().toString().trim());
         map.put("qian",add_demo_edit_qian.getText().toString().trim());
         map.put("sendtime",add_demo_edit_sendtime.getText().toString().trim());

        RetrofitTools.Companion.upload("insertYin",map, parts,new RetrofitTools.IRetrofitResponse(){
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

            if (requestCode==0x999){
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
             else if (requestCode == 0x123) {
                //相册
                List<TImage> timgs = (List<TImage>) data.getSerializableExtra("results");
                ArrayList<String> cameras =  new ArrayList<String>();
                for (TImage item:timgs) {
                    cameras.add(item.getOriginalPath());
                    File file = new File(item.getOriginalPath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("picture_pics", file.getName(), requestFile);
                    parts.add(body);
    //                urls = urls+"#"+file.getName();
                    add_demo_iv_pics.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                }
                //显示图片
            }
    }
}