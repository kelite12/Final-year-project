package com.ztkj.victe.ui.course.activity

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.google.gson.Gson
import com.mylhyl.circledialog.CircleDialog
import com.victe.msit.retrofitlibrary.utils.RetrofitTools
import com.ztkj.victe.MyApplication
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.ui.course.model.Examcontent
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_exam.*

class ExamActivity : BaseActivity() {
    var examcontents = ArrayList<Examcontent>();
    var pos = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        TongJi()
        tongji_head_text.setText("在线考试")
        var examid = intent.getStringExtra("examid");
        getdata(examid);
        exam_quest_up_btn.setOnClickListener {
            //上一题
            if(pos==0){
                toast("已经是第一道题了")
            }else{
                //如果是选择题或者填空获取输入框的值
                if(examcontents[pos].question.studytype.studytypeid!=1){
                    examcontents[pos].userAnswer=exam_question_input_content.text.toString().trim()
                }
                pos--;
                initQuest()
            }
        }
        exam_quest_down_btn.setOnClickListener {
            //下一题
            if(pos>=examcontents.size-1){
                toast("已经是最后一道题了")
            }else{
                //如果是选择题或者填空获取输入框的值
                if(examcontents[pos].question.studytype.studytypeid!=1){
                    examcontents[pos].userAnswer=exam_question_input_content.text.toString().trim()
                }
                pos++;
                initQuest()
            }
        }
        exam_submit.setOnClickListener {
            var flag = false;
            for(ctts in examcontents){
                if("".equals(ctts.userAnswer)){
                    flag = true;
                    CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setText("您还有试题没做完，是否确定交卷？")
                        .setPositive("确定", {
                            addExam();
                        })
                        .setNegative("取消", null)
                        .show()

                    break;
                }
            }
            if(!flag){
                addExam();
            }
        }
    }

    fun addExam(){
        var map = HashMap<String,String>();
        map.put("exams", Gson().toJson(examcontents));
        map.put("userid",MyApplication.user.userid.toString());
        RetrofitTools.post("insertExamrecordList",map,String::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                toast("提交成功");
                finish();
            }

            override fun failure(msg: String) {
                toast("提交失败")
            }

        })
    }

    fun getdata(examid:String){
        examcontents.clear();
        var map = HashMap<String,String>();
        map.put("examid",examid);

        RetrofitTools.post("getExamcontentByExamid",map,Examcontent::class.java,object:RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                 examcontents= succ as ArrayList<Examcontent>;
                if(examcontents.size>0){
                    initQuest();
                }else{
                    toast("暂无试题");
                    finish();
                }
            }

            override fun failure(msg: String) {
                toast("获取试卷内容失败")
            }

        })
    }

    fun initQuest(){
        exam_question_options_rg.removeAllViews();
        exam_question_input_content.setText("");

        exam_question_title.text="${pos+1}、${examcontents[pos].question.name}"
        if(examcontents[pos].question.studytype.studytypeid==1){
            //选择题
            exam_question_options_rg.visibility = View.VISIBLE
            exam_question_input_content.visibility = View.GONE
            var i=65;
            for(rbobj in examcontents[pos].question.qoptions){
                var rb = RadioButton(this);
                rb.setText(rbobj.name+"、"+rbobj.content)
                if(examcontents[pos].userAnswer.equals(rbobj.name)){
                    rb.isChecked=true
                }
                rb.id=i;
                exam_question_options_rg.addView(rb)
                i++;
            }
            exam_question_options_rg.setOnCheckedChangeListener { group, checkedId ->
                examcontents[pos].userAnswer = (checkedId.toChar()).toString()
            }
        }
        else{
            //填空或简答题
            exam_question_options_rg.visibility = View.GONE
            exam_question_input_content.visibility = View.VISIBLE
            exam_question_input_content.setText(examcontents[pos].userAnswer)
        }
    }
}
