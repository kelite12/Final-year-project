package com.ztkj.victe.ui.yin;

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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mylhyl.circledialog.CircleDialog;
import com.victe.msit.retrofitlibrary.utils.RetrofitTools;
import com.ztkj.victe.MyApplication;
import com.ztkj.victe.R;
import com.ztkj.victe.ui.yin.model.Yin;
import com.ztkj.victe.utils.BaseAdapterRecyclerView;
import com.ztkj.victe.ui.yin.AddYinActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YinFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button yin_searchbtn;
    private EditText yin_searchtitle;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yin,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.fragment_yin_recyview);
        yin_searchtitle = getView().findViewById(R.id.fragment_yin_searchtitle);
        fab=getView().findViewById(R.id.fragment_yin_fab);
        yin_searchbtn = getView().findViewById(R.id.fragment_yin_searchbtn);
         yin_searchbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getData();
                }
            });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddYinActivity.class));
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
        map.put("title",yin_searchtitle.getText().toString().trim());
        RetrofitTools.Companion.post("getAllYin2",map, Yin.class,new RetrofitTools.IRetrofitResponse() {
            public void success(Object succ){
                final ArrayList<Yin> newsList = (ArrayList<Yin>) succ;
                if (newsList!=null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Myadapter myadapter = new Myadapter(newsList,R.layout.yin_list_item);
                    recyclerView.setAdapter(myadapter);
                    myadapter.setOnItemClickListener(new BaseAdapterRecyclerView.OnItemClickListener() {
                        @Override
                        public void onItemClick(@NonNull RecyclerView parent, @NonNull View view, int position) {
                            Intent intent = new Intent(getActivity(),YinDetailActivity.class);
                            intent.putExtra("yin",newsList.get(position));
                            startActivity(intent);
                        }
                    });
                    myadapter.setOnItemLongClickListener(new BaseAdapterRecyclerView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(@NonNull RecyclerView parent, @NonNull View view, final int position) {
//                            new CircleDialog.Builder(getActivity())
//                                    .setTitle("提示")
//                                    .setText("是否确定删除？")
//                                    .setPositive("确定", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            delete(newsList.get(position).getYinid()+"");
//                                        }
//                                    })
//                                    .setNegative("取消",new View.OnClickListener(){
//
//                                        @Override
//                                        public void onClick(View view) {
//
//                                        }
//                                    })
//                                    .show();
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
        map.put("yinid",newsid);
        RetrofitTools.Companion.post("deleteYin", map, String.class, new RetrofitTools.IRetrofitResponse() {
            public void success(Object succ){
                if ("true".equals(succ)){
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    getData();
                }else{
                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
            public void failure(String succ){

            }
        });
    }

    private class Myadapter extends BaseAdapterRecyclerView<Yin> {

        public Myadapter(@NonNull List<? extends Yin> list, int itemLayoutId) {
            super(list, itemLayoutId);
        }

        @Override
        public void convert(@NonNull RecyclerHolder holder, Yin item, int position, boolean isScrolling) {
            TextView list_item_yinid = holder.itemView.findViewById(R.id.yin_list_item_yinid);
            list_item_yinid.setText(item.getYinid()+"");
            TextView list_item_title = holder.itemView.findViewById(R.id.yin_list_item_title);
            list_item_title.setText(item.getTitle()+"");
            ImageView yin_list_item_iv = holder.itemView.findViewById(R.id.yin_list_item_iv);
            Glide.with(holder.itemView.getContext()).load(MyApplication.Companion.getHostFile()+item.getPics()).into(yin_list_item_iv);
            TextView list_item_sendtime = holder.itemView.findViewById(R.id.yin_list_item_sendtime);
            list_item_sendtime.setText(item.getSendtime()+"");
        }
    }

}