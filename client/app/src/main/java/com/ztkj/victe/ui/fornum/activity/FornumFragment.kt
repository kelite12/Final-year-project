package com.ztkj.victe.ui.fornum.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mylhyl.circledialog.CircleDialog
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.fornum.model.Fornum
import com.ztkj.victe.utils.AlarmUtils
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import kotlinx.android.synthetic.main.fornum_item.view.*
import kotlinx.android.synthetic.main.frag_fornum.*
import kotlinx.android.synthetic.main.frag_line_item2.view.*

class FornumFragment: LazyFragment(){
    companion object {
        fun newInstance(isLazyLoad: Boolean): FornumFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = FornumFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onFragmentStartLazy() {
        getData()
        activity!!.findViewById<ImageView>(R.id.main_send_iv).visibility = View.VISIBLE
    }

    override fun onFragmentStopLazy() {
        activity!!.findViewById<ImageView>(R.id.main_send_iv).visibility = View.GONE
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_fornum)
    }

    override fun onResumeLazy() {
        activity!!.findViewById<ImageView>(R.id.main_send_iv).visibility = View.VISIBLE
        getData()
        inserFornum.setOnClickListener {
            var intent = Intent(activity,InsertFornumActivity::class.java)
            intent.putExtra("status",1)
            startActivity(intent);
        }
       activity!!.findViewById<ImageView>(R.id.main_send_iv).setOnClickListener {
           var intent = Intent(activity,InsertFornumActivity::class.java)
           intent.putExtra("status",1)
           startActivity(intent);
       }
    }

    override fun onDestroyViewLazy() {
    }

    fun getData(){
        var map = HashMap<String,String>()
        map.put("userid",MyApplication.user.userid.toString())
        map.put("status","1")
        RetrofitTools.post("getAllMyFornum",map,Fornum::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var fornums = succ as List<Fornum>;
                fornum_recy.layoutManager = LinearLayoutManager(activity)
                var adapter = Myadapter(fornums);
                fornum_recy.adapter = adapter;
                adapter.setOnItemLongClickListener(object :BaseAdapterRecyclerView.OnItemLongClickListener{
                    override fun onItemLongClick(
                        parent: RecyclerView,
                        view: View,
                        position: Int
                    ): Boolean {
                        CircleDialog.Builder(activity!!)
                            .setTitle("提示")
                            .setText("您确定要删除吗")
                            .setPositive("确定", {
                                deleteData(fornums.get(position).fornumid.toString())

                            }).setNegative("取消",null)
                            .show()
                        return true
                    }

                })

            }

            override fun failure(msg: String) {
                toast("获取网络数据失败")
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

    class Myadapter(mList:List<Fornum>):BaseAdapterRecyclerView<Fornum>(mList,R.layout.frag_line_item2){
        override fun convert(
            holder: RecyclerHolder,
            item: Fornum,
            position: Int,
            isScrolling: Boolean
        ) {
            with(holder.itemView){
                frag_line_item2_tv.setText(item.content)
                frag_line_item2_status.setText(item.remark1)
//                fornum_item_time.setText(item.sendtime)
//                fornum_item_head_content.setText(item.content)
//                fornum_item_head_name.setText(item.user.username)
//                Glide.with(holder.itemView.context).load(MyApplication.hostFile+item.user.headPic).error(R.mipmap.ic_launcher).into(fornum_item_head_pic)
//                var images =  item.urls.split("#").toTypedArray();
//                images.forEachIndexed { index, s ->
//                    if(s.startsWith("http")){
//                        //images=MyApplication.hostFile+images.get(index);
//                        images[index] = MyApplication.hostFile+images.get(index);
//                    }
//                }
//                for ((position,item) in images.withIndex()){
//                    if (!item.startsWith("http")){
//                        images[position] = "${MyApplication.hostFile}${item}"
//                    }
//                }
//                fornum_item_head_multi.setList(images.toMutableList());
            }
        }

    }

}