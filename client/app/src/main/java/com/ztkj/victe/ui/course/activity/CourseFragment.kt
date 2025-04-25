package com.victe.vproject.modules.course

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.mylhyl.circledialog.CircleDialog
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import com.ztkj.victe.ui.course.activity.ColorUtils
import com.ztkj.victe.ui.course.activity.CornerTextView
import com.ztkj.victe.ui.course.activity.InsertCourseActivity
import com.ztkj.victe.ui.course.model.Course
import kotlinx.android.synthetic.main.frag_course.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by 13526 on 2018/3/21.
 * 课程表
 */
class CourseFragment: LazyFragment(){
    companion object {
        fun newInstance(isLazyLoad: Boolean): CourseFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = CourseFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
    var mWeekViews: ArrayList<LinearLayout>? = ArrayList<LinearLayout>();
    private var itemHeight: Int = 0
    private val maxSection = 12
    private var courses = ArrayList<List<Course>>();
    override fun onFragmentStartLazy() {
        getdata();
    }

    override fun onFragmentStopLazy() {

    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
    setContentView(R.layout.frag_course)
        getdata();
    }

    fun getdata(){
        var map = HashMap<String,String>();
        RetrofitTools.get("getAllWeekCourse",map,Course::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                courses.clear()
                var data = succ.toString();
                var parser =  JsonParser();
                var jsonarray = parser.parse(data).getAsJsonArray();
                for (element in jsonarray) {
                    var childlist = ArrayList<Course>();
                    var cparser =  JsonParser();
                    var cjsonarray = cparser.parse(element.toString()).getAsJsonArray();
                    for (celement in cjsonarray){
                        childlist.add(Gson().fromJson(celement, Course::class.java));
                    }
                    courses.add(childlist);
                }

                clearChildView()
                initWeekCourseView()
            }

            override fun failure(msg: String) {
                toast("获取数据失败")
            }
        },false)
    }

    override fun onResumeLazy() {
        itemHeight = resources.getDimensionPixelSize(R.dimen.sectionHeight)
        mWeekViews!!.add(weekPanel_1)
        mWeekViews!!.add(weekPanel_2)
        mWeekViews!!.add(weekPanel_3)
        mWeekViews!!.add(weekPanel_4)
        mWeekViews!!.add(weekPanel_5)
        mWeekViews!!.add(weekPanel_6)
        mWeekViews!!.add(weekPanel_7)
        initWeekNameView()
        initSectionView()
//        initWeekCourseView()
        setRefreshListener()
    }

    override fun onDestroyViewLazy() {

    }


    /**
     * 初始化课程表
     */
    private fun initWeekCourseView() {
        for (i in mWeekViews!!.indices) {
//            initWeekPanel(mWeekViews!!.get(i), CourseDao.getCourseData()[i])
            initWeekPanel(mWeekViews!!.get(i), courses.get(i))
        }
    }

    /**
     * 下拉刷新
     */
    private fun setRefreshListener() {
        mFreshLayout.setLoadMore(false)
        mFreshLayout.setMaterialRefreshListener(object : MaterialRefreshListener() {
            override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
                clearChildView()
//                initWeekCourseView()
                getdata()
                mFreshLayout.postDelayed(Runnable { mFreshLayout.finishRefreshing() }, 500)
            }

        })
    }

    /**
     * 顶部周一到周日的布局
     */
    private fun initWeekNameView() {
        for (i in 0 until mWeekViews!!.size + 1) {
            val tvWeekName = TextView(activity)
            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.gravity = Gravity.CENTER or Gravity.CENTER_HORIZONTAL
            if (i != 0) {
                lp.weight = 1f
                tvWeekName.text = "周" + intToZH(i)
                if (i == getWeekDay()) {
                    tvWeekName.setTextColor(Color.parseColor("#FF0000"))
                } else {
                    tvWeekName.setTextColor(Color.parseColor("#4A4A4A"))
                }
            } else {
                lp.weight = 0.8f
                tvWeekName.text = getMonth().toString() + "月"
            }
            tvWeekName.gravity = Gravity.CENTER_HORIZONTAL
            tvWeekName.layoutParams = lp
            weekNames.addView(tvWeekName)
        }
    }

    /**
     * 左边节次布局，设定每天最多12节课
     */
    private fun initSectionView() {
        for (i in 1..maxSection) {
            val tvSection = TextView(activity)
            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, resources.getDimensionPixelSize(R.dimen.sectionHeight))
            lp.gravity = Gravity.CENTER
            tvSection.gravity = Gravity.CENTER
            tvSection.text = i.toString()
            tvSection.layoutParams = lp
            sections.addView(tvSection)
        }
    }

    /**
     * 当前星期
     */
    fun getWeekDay(): Int {
        var w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
        if (w <= 0) {
            w = 7
        }
        return w
    }

    /**
     * 当前月份
     */
    fun getMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1
    }

    /**
     * 每次刷新前清除每个LinearLayout上的课程view
     */
    private fun clearChildView() {
        for (i in mWeekViews!!.indices) {
            if (mWeekViews!!.get(i) != null)
                if (mWeekViews!!.get(i).getChildCount() > 0)
                    mWeekViews!!.get(i).removeAllViews()
        }
    }


    fun initWeekPanel(ll: LinearLayout?, data: List<Course>?) {

        if (ll == null || data == null || data.size < 1)
            return
        var firstCourse = data[0]
        for (i in data.indices) {
            val courseModel = data[i]

            if (courseModel.section === 0 || courseModel.courseflag === 0)
                return
            val frameLayout = FrameLayout(activity)

            val tv = CornerTextView(activity,
                    ColorUtils.getCourseBgColor(courseModel.courseflag),
                    dip2px(activity!!, 3f))
            val frameLp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    itemHeight * courseModel.sectionspan)
            val tvLp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)

            if (i == 0) {
                frameLp.setMargins(0, (courseModel.section - 1) * itemHeight, 0, 0)
            } else {
                frameLp.setMargins(0, (courseModel.section - (firstCourse.section + firstCourse.sectionspan)) * itemHeight, 0, 0)
            }
            tv.layoutParams = tvLp
            tv.gravity = Gravity.CENTER
            tv.setTextSize(12f)
            tv.setTextColor(Color.parseColor("#FFFFFF"))
            tv.text = courseModel.coursename + "\n @" + courseModel.classroom

            frameLayout.layoutParams = frameLp
            frameLayout.addView(tv)
            frameLayout.setPadding(2, 2, 2, 2)
            ll.addView(frameLayout)
            firstCourse = courseModel
            tv.setOnClickListener {
                showToast(courseModel.coursename)
            }
            tv.setOnLongClickListener {
                CircleDialog.Builder(activity!!)
                        .setTitle("提示")
                        .setItems(arrayOf("添加","删除"),object: AdapterView.OnItemClickListener{
                            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                when(p2){
                                    0 ->{
                                        startActivity(Intent(activity, InsertCourseActivity::class.java))
                                    }
                                    1->{
                                        deleteCourse(courseModel.courseid.toString())
                                    }
                                }

                            }

                        })
                        .show()
                true
            }
        }
    }

    private fun deleteCourse(id:String){
        var map = HashMap<String,String>();
        map.put("courseid",id)
        RetrofitTools.post("deleteCourseByCourseid",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("true".equals(succ)){
                    toast("删除成功")
                    getdata()
                }else{
                    toast("删除失败")
                }
            }


            override fun failure(msg: String) {
                toast("删除失败")
            }

        })
//        (activity as BaseActivity).service.getStringData("deleteCourse",map).enqueue(object : Callback<String> {
//            override fun onFailure(call: Call<String>?, t: Throwable?) {
//                toast("网络异常${call.toString()}----${t.toString()}")
//            }
//
//            override fun onResponse(call: Call<String>?, response: Response<String>?) {
//               if ("true".equals(response!!.body())){
//                   toast("删除成功")
//                   getdata();
//               }
//                else{
//                   toast("删除失败")
//               }
//            }
//
//        })
    }

    /**
     * Toast
     */
    private fun showToast(msg: String) {
        if (TextUtils.isEmpty(msg))
            return
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 数字转换中文
     */
    fun intToZH(i: Int): String {
        val zh = arrayOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九")
        val unit = arrayOf("", "十", "百", "千", "万", "十", "百", "千", "亿", "十")

        var str = ""
        var sb = StringBuffer(i.toString())
        sb = sb.reverse()
        var r = 0
        var l = 0
        for (j in 0 until sb.length) {
            r = Integer.valueOf(sb.substring(j, j + 1))!!
            if (j != 0)
                l = Integer.valueOf(sb.substring(j - 1, j))!!
            if (j == 0) {
                if (r != 0 || sb.length == 1)
                    str = zh[r]
                continue
            }
            if (j == 1 || j == 2 || j == 3 || j == 5 || j == 6 || j == 7 || j == 9) {
                if (r != 0)
                    str = zh[r] + unit[j] + str
                else if (l != 0)
                    str = zh[r] + str
                continue
            }
            if (j == 4 || j == 8) {
                str = unit[j] + str
                if (l != 0 && r == 0 || r != 0)
                    str = zh[r] + str
                continue
            }
        }
        if (str == "七")
            str = "日"
        return str
    }


}