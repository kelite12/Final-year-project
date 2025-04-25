package com.ztkj.victe


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.mylhyl.circledialog.CircleDialog
import com.ztkj.victe.ui.baidu.BaiduActivity
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.course.activity.CourseStudyFragment
import com.ztkj.victe.ui.fornum.activity.FornumWeiboFragment
import com.ztkj.victe.ui.fornum.activity.ShowMsgActivity
import com.ztkj.victe.ui.health.Finan4Fragment
import com.ztkj.victe.ui.health.HealthActivity
import com.ztkj.victe.ui.health.HealthFragment
import com.ztkj.victe.ui.news.activity.NewsFragment
import com.ztkj.victe.ui.step.activity.HistoryActivity
import com.ztkj.victe.ui.step.activity.StepMainActivity
import com.ztkj.victe.ui.step.step.bean.StepData
import com.ztkj.victe.ui.step.step.utils.DbUtils
import com.ztkj.victe.ui.userinfo.UpdatePassActivity
import com.ztkj.victe.ui.userinfo.activity.ShowPersonActivity
import com.ztkj.victe.ui.userinfo.activity.SignCalendarActivity
import com.ztkj.victe.ui.userinfo.activity.UsersFragment
import com.ztkj.victe.ui.xiu.XiuFragment
import com.ztkj.victe.ui.yin.YinFragment
import com.ztkj.victe.utils.AlarmUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import com.ztkj.victe.utils.DateUtils
import kotlinx.android.synthetic.main.activity_insert_fornum.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
       // setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        setViewPager();
        setBottomclick();

        Glide.with(applicationContext).load(MyApplication.hostFile+MyApplication.user.headPic).error(R.mipmap.ic_launcher).into(nav_view.getHeaderView(0).findViewById<ImageView>(R.id.headImageView))
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.nav_head_name_tv).text = "Welcome！${MyApplication.user.nickname}"

        initData();
    }

    val fragments = ArrayList<Fragment>()
    fun setViewPager(){
        fragments.add(NewsFragment.newInstance(true))
        fragments.add(FornumWeiboFragment.newInstance(true))
        fragments.add(UsersFragment.newInstance(true))
        fragments.add(Finan4Fragment())
        fragments.add(YinFragment())

        tbviewpager.adapter = MyPagerAdapter((supportFragmentManager))
        tbviewpager.offscreenPageLimit = 8
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_person->{
                startActivity(Intent(this,ShowPersonActivity::class.java))
            }
            R.id.nav_health->{
                startActivity(Intent(this,HealthActivity::class.java))
            }
            R.id.nav_history->{
                startActivity(Intent(this,HistoryActivity::class.java))
            }
            R.id.nav_cang->{
                startActivity(Intent(this,StepMainActivity::class.java))
            }
            R.id.nav_baidu->{
                startActivity(Intent(this,BaiduActivity::class.java))
            }

            R.id.nav_updatepass->{
                startActivity(Intent(this,UpdatePassActivity::class.java))
            }

            R.id.nav_gallery->{
                startActivity(Intent(this,SignCalendarActivity::class.java))
            }


            R.id.nav_aboutme ->{
                var cal = Calendar.getInstance()
                TimePickerDialog(this,object :TimePickerDialog.OnTimeSetListener{
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                        cal.set(Calendar.MINUTE,minute)
                        toast("Setting successful")
                       // selectCamera.setText("${hourOfDay}:${minute}")
                        var intent = Intent(this@MainActivity, ShowMsgActivity::class.java)
                        var time = System.currentTimeMillis();
                        intent.putExtra("title","time out")
//                        Log.e("TAG","====${cal.timeInMillis-time}====")
                        AlarmUtils.startAlarmActivity(this@MainActivity,cal.timeInMillis,intent);

                    }

                },cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
            }
            R.id.nav_share -> {
                var textIntent = Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "Thank you everyone, this is a test software sharing");
                startActivity(Intent.createChooser(textIntent, "share"));

            }
            R.id.nav_send ->{
                //退出
                CircleDialog.Builder(this)
                    .setTitle("prompt")
                    .setText("Are you sure to exit")
                    .setPositive("sure", { finish() })
                    .setNegative("cancel", null)
                    .show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    fun setBottomclick(){
        initBottom(0)
        index_home_linear.setOnClickListener {
            initBottom(0)
        }
        index_contact_linear.setOnClickListener {
            initBottom(1);
        }
        index_find_linear.setOnClickListener {
            initBottom(2);
        }
        index_me_linear.setOnClickListener {
            initBottom(3);
        }
        index_me_linear2.setOnClickListener {
            initBottom(4);
        }
        tbviewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                initBottom(position)
            }

        })
    }

    private fun initBottom( position: Int){
        index_home_button.setImageResource(R.drawable.base_home1_normal_bg)
        index_contact_button.setImageResource(R.drawable.base_circle_normal_bg)
        index_find_button.setImageResource(R.drawable.base_sort_normal_bg)
        index_me_button.setImageResource(R.drawable.base_personal1_normal_bg)
        index_me_button2.setImageResource(R.drawable.cour)
        index_home_text.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        index_contact_text.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        index_find_text.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        index_me_text.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        index_me_text2.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        when(position){
            0->{
                main_head_title.setText(index_home_text.text.toString())
                index_home_button.setImageResource(R.drawable.base_home1_click_bg)
                index_home_text.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            1->{
                main_head_title.setText(index_contact_text.text.toString())
                index_contact_button.setImageResource(R.drawable.base_circle_click_bg)
                index_contact_text.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            2 ->{
                main_head_title.setText(index_find_text.text.toString())
                index_find_button.setImageResource(R.drawable.base_sort_click_bg)
                index_find_text.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            3 ->{
                main_head_title.setText(index_me_text.text.toString())
                index_me_button.setImageResource(R.drawable.base_personal1_click_bg)
                index_me_text.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            4->{
                main_head_title.setText(index_me_text2.text.toString())
                index_me_button2.setImageResource(R.drawable.cour_sel)
                index_me_text2.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
        }
        tbviewpager.setCurrentItem(position)
    }

    private inner class MyPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }


    fun initData(){
        if (DbUtils.getLiteOrm() != null) {
            var stepDatas = DbUtils.getQueryAll(StepData::class.java)
            if(stepDatas!=null){
                var sum = 0;
                for(item in stepDatas){
                    sum = sum + item.step.toInt()
                }
                MyApplication.sumStep = sum;
            }
        }

    }
}
