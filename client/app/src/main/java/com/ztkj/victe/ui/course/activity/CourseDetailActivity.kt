package com.ztkj.victe.ui.course.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import com.xiao.nicevideoplayer.TxVideoPlayerController
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.course.model.Details
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_course_detail.*

/**
 * 课程章节学习
 */
class CourseDetailActivity : BaseActivity() {
   // var path = "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"
    var fragments = ArrayList<Fragment>();
    var titles = ArrayList<String>();
    lateinit var detail:Details;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)
        TongJi()
        tongji_head_text.setText("课程详情")
        detail = intent.getSerializableExtra("detail") as Details;
        nice_video_player.setPlayerType(NiceVideoPlayer.TYPE_NATIVE)
        nice_video_player.setUp(MyApplication.hostFile+detail.video,null);
        val controller = TxVideoPlayerController(this)
        var imageview = controller.imageView();
        controller.setTitle(detail.title)
//        Glide.with(this)
//            .load(MyApplication.hostFile+detail.video)
//            .placeholder(R.mipmap.ic_launcher)
//            .crossFade()
//            .into(imageview);
        nice_video_player.setController(controller)

        nice_video_player.start()
        init();
    }

    fun init(){
        fragments.add(DetailFragemnt.newInstance(true,detail));
        fragments.add(DiscussFragemnt.newInstance(true,detail.detailsid.toString()));
        titles.add("详情")
        titles.add("评论")
        course_viewpager.setAdapter(object:FragmentStatePagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles.get(position)
            }

        })
        course_viewpager.offscreenPageLimit=3
        course_detail_tabs.setupWithViewPager(course_viewpager)
    }

    override fun onStop() {
        super.onStop()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed()
    }

    override fun onRestart() {
        super.onRestart()
    }
}
