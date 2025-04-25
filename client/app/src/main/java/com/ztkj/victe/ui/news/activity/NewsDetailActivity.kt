package com.ztkj.victe.ui.news.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.news.model.News
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_news_detail_activity.*
import kotlinx.android.synthetic.main.news_list_item.*

class NewsDetailActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail_activity)
        TongJi()
        tongji_head_text.setText("Information Details")

        var news1=intent.getSerializableExtra("news") as News;
        news_detail_item_title.setText(news1.title);
        news_detail_item_contet.setText("\u3000"+news1.content);
        news_detail_item_time.setText(news1.sendtime);
        Log.e("TAG",news1.title)
        news1.content=news1.content.replace("src=\"","src=\""+ MyApplication.host)
        Log.e("TAG",news1.content)
        RichText.initCacheDir(this);
        RichText.from(news1.content).bind(this)
            .showBorder(false)
            .size(ImageHolder.WRAP_CONTENT, ImageHolder.WRAP_CONTENT)
            .into(news_detail_item_contet);

    }

    override fun onDestroy() {
        super.onDestroy()
        RichText.clear(this);
    }

}
