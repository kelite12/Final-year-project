package com.ztkj.victe.ui.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mylhyl.circledialog.CircleDialog;
import com.victe.msit.retrofitlibrary.utils.RetrofitTools;
import com.ztkj.victe.MyApplication;
import com.ztkj.victe.R;
import com.ztkj.victe.ui.health.model.Health;
import com.ztkj.victe.utils.BaseAdapterRecyclerView;
import com.ztkj.victe.ui.health.AddHealthActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HealthFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button health_searchbtn;
    private EditText health_searchtext;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.fragment_health_recyview);
        health_searchbtn = getView().findViewById(R.id.fragment_health_searchbtn);
        health_searchtext = getView().findViewById(R.id.fragment_health_searchtext);
        fab=getView().findViewById(R.id.fragment_health_fab);
        health_searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddHealthActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        HashMap<String,String> map = new HashMap<>();
        map.put("title","%"+health_searchtext.getText().toString().trim()+"%");
        map.put("userid",MyApplication.user.getUserid()+"");
        RetrofitTools.Companion.post("getAllHealth",map, Health.class,new RetrofitTools.IRetrofitResponse() {
            public void success(Object succ){
                final ArrayList<Health> newsList = (ArrayList<Health>) succ;
                if (newsList!=null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Myadapter myadapter = new Myadapter(newsList,R.layout.health_list_item);
                    recyclerView.setAdapter(myadapter);
                    myadapter.setOnItemClickListener(new BaseAdapterRecyclerView.OnItemClickListener() {
                        @Override
                        public void onItemClick(@NonNull RecyclerView parent, @NonNull View view, int position) {
//                            Intent intent = new Intent(getActivity(),HealthDetailActivity.class);
//                            intent.putExtra("health",newsList.get(position));
//                            startActivity(intent);
                        }
                    });
                    myadapter.setOnItemLongClickListener(new BaseAdapterRecyclerView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(@NonNull RecyclerView parent, @NonNull View view, final int position) {
                            if (MyApplication.user.getUserid()==newsList.get(position).getUserid()){
                                new CircleDialog.Builder(getActivity())
                                        .setTitle("prompt")
                                        .setText("Are you sure to delete？")
                                        .setPositive("ok", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                delete(newsList.get(position).getHealthid()+"");
                                            }
                                        })
                                        .setNegative("cancel",new View.OnClickListener(){

                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .show();
                            }

                            return false;
                        }
                    });
                }
            }
            public void failure(String succ){

            }
        });
    }

    //删除
    private void delete(String newsid){
        HashMap<String,String> map = new HashMap<>();
        map.put("healthid",newsid);
        RetrofitTools.Companion.post("deleteHealth", map, String.class, new RetrofitTools.IRetrofitResponse() {
            public void success(Object succ){
                if ("true".equals(succ)){
                    Toast.makeText(getActivity(), "Delete successfully", Toast.LENGTH_SHORT).show();
                    getData();
                }else{
                    Toast.makeText(getActivity(), "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
            public void failure(String succ){

            }
        });
    }

    private class Myadapter extends BaseAdapterRecyclerView<Health> {

        public Myadapter(@NonNull List<? extends Health> list, int itemLayoutId) {
            super(list, itemLayoutId);
        }

        @Override
        public void convert(@NonNull RecyclerHolder holder, Health item, int position, boolean isScrolling) {
            
            TextView list_item_healthid = holder.itemView.findViewById(R.id.health_list_item_healthid);
            list_item_healthid.setText(item.getHealthid()+"");
            
            TextView list_item_age = holder.itemView.findViewById(R.id.health_list_item_age);
            list_item_age.setText(item.getAge()+"");
            
            TextView list_item_sex = holder.itemView.findViewById(R.id.health_list_item_sex);
            list_item_sex.setText(item.getSex()+"");
            
            TextView list_item_xuey = holder.itemView.findViewById(R.id.health_list_item_xuey);
            list_item_xuey.setText(item.getXuey()+"");
            
            TextView list_item_xuet = holder.itemView.findViewById(R.id.health_list_item_xuet);
            list_item_xuet.setText(item.getXuet()+"");
            
            TextView list_item_xuez = holder.itemView.findViewById(R.id.health_list_item_xuez);
            list_item_xuez.setText(item.getXuez()+"");
            
            TextView list_item_xueg = holder.itemView.findViewById(R.id.health_list_item_xueg);
            list_item_xueg.setText(item.getXueg()+"");
            
            TextView list_item_userid = holder.itemView.findViewById(R.id.health_list_item_userid);
            list_item_userid.setText(item.getUser().getUsername()+"");
            
            TextView list_item_remark1 = holder.itemView.findViewById(R.id.health_list_item_remark1);
            TextView health_list_item_sendtime = holder.itemView.findViewById(R.id.health_list_item_sendtime);
            health_list_item_sendtime.setText(item.getSendtime()+"");
            
        }
    }

}