package com.ztkj.victe.ui.fornum.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mylhyl.circledialog.CircleDialog
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.victe.msit.retrofitlibrary.utils.RetrofitTools.IRetrofitResponse
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.fornum.model.Fornum
import com.ztkj.victe.utils.AlarmUtils
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.fornum_weibo_item.view.*

import kotlinx.android.synthetic.main.frag_fornum_weibo.*


class FornumWeiboFragment: LazyFragment(){
    companion object {
        fun newInstance(isLazyLoad: Boolean): FornumWeiboFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = FornumWeiboFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onFragmentStartLazy() {
        getData()
    }

    override fun onFragmentStopLazy() {
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_fornum_weibo)
    }

    override fun onResumeLazy() {
        getData()
        inserFornum.setOnClickListener {
            startActivity(Intent(activity,InsertFornumActivity::class.java));
        }
    }

    override fun onDestroyViewLazy() {
    }

    fun getData(){
        RetrofitTools.post("getAllFornum",Fornum::class.java,object : IRetrofitResponse{
            override fun <T> success(succ: T) {
                var fornums = succ as List<Fornum>;
                //判断是否点赞
                fornums.forEach{its->
                    its.praises.forEach {
                        if(it.userid==MyApplication.user.userid){
                            its.isFlag=true;
                        }
                    }
                }
                fornum_recy.layoutManager = LinearLayoutManager(activity)
                var adapter = Myadapter(fornums);
                fornum_recy.adapter = adapter;
                adapter.setOnItemClickListener(object :BaseAdapterRecyclerView.OnItemClickListener{
                    override fun onItemClick(parent: RecyclerView, view: View, position: Int) {
                        var intent = Intent(activity,FornumDetailActivity::class.java)
                        intent.putExtra("fornum",fornums.get(position));
                        startActivity(intent);
                    }

                })

                adapter.setOnItemLongClickListener(object :BaseAdapterRecyclerView.OnItemLongClickListener{
                    override fun onItemLongClick(
                        parent: RecyclerView,
                        view: View,
                        position: Int
                    ): Boolean {

                        if(MyApplication.user.userid==fornums[position].userid){
                            CircleDialog.Builder(activity!!)
                                .setTitle("promt")
                                .setText("Are you sure you want to delete it")
                                .setPositive("ok", {
                                    deleteData(fornums.get(position).fornumid.toString())

                                }).setNegative("cancel",null)
                                .show()
                        }

                        return true;
                    }

                })
            }

            override fun failure(msg: String) {
                toast("Failed to obtain network data")
            }

        })

    }


    fun deleteData(fornumid:String){
        var map = HashMap<String,String>()
        map.put("fornumid",fornumid);
        RetrofitTools.get("deleteFornum",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                AlarmUtils.stopAlarmActivity(activity,Intent(activity,ShowMsgActivity::class.java))
                getData()
            }

            override fun failure(msg: String) {
                toast(msg)
            }

        },false)
    }

    inner class Myadapter(mList:List<Fornum>):BaseAdapterRecyclerView<Fornum>(mList,R.layout.fornum_weibo_item){

        override fun convert(
            holder: RecyclerHolder,
            item: Fornum,
            position: Int,
            isScrolling: Boolean
        ) {
            with(holder.itemView){
                fornum_item_time.setText(item.sendtime)
                fornum_item_head_content.setText(item.content)
                fornum_item_head_name.setText(item.user.username)
                if(item.discusses.size>0){
                    fornum_weibo_item_pl_count.setText(item.discusses.size.toString())
                }
                else{
                    fornum_weibo_item_pl_count.setText("comment")
                }
                //判断点赞
                if(item.praises.size>0){
                    fornum_weibo_item_dz_line_tv.setText(item.praises.size.toString())
                    if(item.isFlag){
                        fornum_weibo_item_dz_line_iv.setImageResource(R.mipmap.dianzan_click)
                    }
                    else{
                        fornum_weibo_item_dz_line_iv.setImageResource(R.mipmap.dianzan)
                    }
                }else{
                    fornum_weibo_item_dz_line_tv.setText("give the thumbs-up")
                }


                Glide.with(holder.itemView.context).load(MyApplication.hostFile+item.user.headPic).error(R.mipmap.ic_launcher).into(fornum_item_head_pic)
                var images =  item.urls.split("#").toTypedArray();
                images.forEachIndexed { index, s ->
                    if(s.startsWith("http")){
                        //images=MyApplication.hostFile+images.get(index);
                        images[index] = MyApplication.hostFile+images.get(index);
                    }
                }
                for ((position,item) in images.withIndex()){
                    if (!item.startsWith("http")){
                        images[position] = "${MyApplication.hostFile}${item}"
                    }
                }

                fornum_item_head_multi.setList(images.toMutableList());

                //点赞的点击事件
                fornum_weibo_item_dz_line.setOnClickListener {
                    if(item.isFlag){
                        //取消点赞
                        fornum_weibo_item_dz_line_iv.setImageResource(R.mipmap.dianzan)
                        fornum_weibo_item_dz_line_tv.setText((item.praises.size-1).toString())
                        insertData(item.fornumid.toString(),"deleteMyPraise");
                    }
                    else{
                        //点赞
                        fornum_weibo_item_dz_line_iv.setImageResource(R.mipmap.dianzan_click)
                        fornum_weibo_item_dz_line_tv.setText((item.praises.size+1).toString())
                        insertData(item.fornumid.toString(),"insertPraise");
                    }

                }

            }

        }

    }


    fun insertData(targetid:String,method:String){
        var map = HashMap<String,String>();
        map.put("userid",MyApplication.user.userid.toString());
        map.put("targetid",targetid);
        RetrofitTools.post(method,map,String::class.java, object:IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("true".equals(succ)){
                    //点赞成功
                }
                else{
                    //点赞失败
                }
            }

            override fun failure(msg: String) {

            }

        })
    }
}