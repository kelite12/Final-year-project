package com.ztkj.victe.ui.news.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.banners.Banners
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.news.model.News
import com.ztkj.victe.utils.BaseAdapterRecyclerView
import com.ztkj.victe.utils.GlideImageLoader
import kotlinx.android.synthetic.main.frag_news.*
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewsFragment:LazyFragment(){
    companion object {
        fun newInstance(isLazyLoad: Boolean): NewsFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = NewsFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onFragmentStartLazy() {
    }

    override fun onFragmentStopLazy() {
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_news)
        getData();
    }

    override fun onResumeLazy() {
    }

    override fun onDestroyViewLazy() {
    }
    fun getData(){
        RetrofitTools.post("getAllNews",News::class.java,object:RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var news = succ as List<News>;
                //设置布局管理器
                news_recy.layoutManager = LinearLayoutManager(applicationContext);
                //实例化适配器
                var adapter = Myadapter(news);
                //设置适配器
                news_recy.adapter = adapter
                //设置item点击事件
                adapter.setOnItemClickListener(object :BaseAdapterRecyclerView.OnItemClickListener{
                    override fun onItemClick(parent: RecyclerView, view: View, position: Int) {

                        var intent = Intent(activity,NewsDetailActivity::class.java);
                         intent.putExtra("news",news[position]);
                        startActivity(intent)
                    }

                })
            }

            override fun failure(msg: String) {
                toast(msg);
            }

        })
        //获取轮播图
        RetrofitTools.get("getUsedBanners",Banners::class.java,object:RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var banners = succ as List<Banners>;
                var images = ArrayList<String>();
                banners.forEach {
                    images.add(MyApplication.hostFile+it.pic)
                }

                banner.setDelayTime(2000);
                banner.setImages(images.toMutableList()).setImageLoader(GlideImageLoader()).start()
            }

            override fun failure(msg: String) {
                toast("轮播图片请求失败")
            }

        })
    }
    class Myadapter(mlist: List<News>):BaseAdapterRecyclerView<News>(mlist,R.layout.news_list_item){
        override fun convert(
            holder: RecyclerHolder,
            item: News,
            position: Int,
            isScrolling: Boolean
        ) {
            //Log.e("TAG",item.title);
            with(holder.itemView){
                news_list_item_title.text = item.title
                news_list_item_contet.text = Html.fromHtml(item.content)
                news_list_item_time.text = item.sendtime

            }
        }

    }

}