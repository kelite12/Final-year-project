package com.ztkj.victe.ui.xiu;

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
import com.ztkj.victe.ui.xiu.model.Xiu;
import com.ztkj.victe.utils.BaseAdapterRecyclerView;
import com.ztkj.victe.ui.xiu.AddXiuActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XiuFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button xiu_searchbtn;
    private EditText xiu_searchtext;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_xiu,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.fragment_xiu_recyview);
        xiu_searchbtn = getView().findViewById(R.id.fragment_xiu_searchbtn);
        xiu_searchtext = getView().findViewById(R.id.fragment_xiu_searchtext);
        fab=getView().findViewById(R.id.fragment_xiu_fab);
        xiu_searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddXiuActivity.class));
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
        map.put("title","%"+xiu_searchtext.getText().toString().trim()+"%");
        map.put("userid", MyApplication.user.getUserid()+"");
        RetrofitTools.Companion.post("getAllXiu",map, Xiu.class,new RetrofitTools.IRetrofitResponse() {
            public void success(Object succ){
                final ArrayList<Xiu> newsList = (ArrayList<Xiu>) succ;
                if (newsList!=null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Myadapter myadapter = new Myadapter(newsList,R.layout.xiu_list_item);
                    recyclerView.setAdapter(myadapter);
                    myadapter.setOnItemClickListener(new BaseAdapterRecyclerView.OnItemClickListener() {
                        @Override
                        public void onItemClick(@NonNull RecyclerView parent, @NonNull View view, int position) {
                            Intent intent = new Intent(getActivity(),XiuDetailActivity.class);
                            intent.putExtra("xiu",newsList.get(position));
                            startActivity(intent);
                        }
                    });
                    myadapter.setOnItemLongClickListener(new BaseAdapterRecyclerView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(@NonNull RecyclerView parent, @NonNull View view, final int position) {
                            new CircleDialog.Builder(getActivity())
                                    .setTitle("提示")
                                    .setText("是否确定删除？")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            delete(newsList.get(position).getXiuid()+"");
                                        }
                                    })
                                    .setNegative("取消",new View.OnClickListener(){

                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .show();
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
        map.put("xiuid",newsid);
        RetrofitTools.Companion.post("deleteXiu", map, String.class, new RetrofitTools.IRetrofitResponse() {
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

    private class Myadapter extends BaseAdapterRecyclerView<Xiu> {

        public Myadapter(@NonNull List<? extends Xiu> list, int itemLayoutId) {
            super(list, itemLayoutId);
        }

        @Override
        public void convert(@NonNull RecyclerHolder holder, Xiu item, int position, boolean isScrolling) {
            
            TextView list_item_xiuid = holder.itemView.findViewById(R.id.xiu_list_item_xiuid);
            list_item_xiuid.setText(item.getXiuid()+"");
            
            TextView list_item_title = holder.itemView.findViewById(R.id.xiu_list_item_title);
            list_item_title.setText(item.getTitle()+"");
            
            TextView list_item_stime = holder.itemView.findViewById(R.id.xiu_list_item_stime);
            list_item_stime.setText(item.getStime()+"");
            
            TextView list_item_etime = holder.itemView.findViewById(R.id.xiu_list_item_etime);
            list_item_etime.setText(item.getEtime()+"");
            
        }
    }

}