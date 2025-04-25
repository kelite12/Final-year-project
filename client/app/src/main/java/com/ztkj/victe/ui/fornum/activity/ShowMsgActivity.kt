package com.ztkj.victe.ui.fornum.activity

import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.BaseActivity
import com.ztkj.victe.utils.AlarmUtils
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_show_msg.*
import androidx.core.content.ContextCompat.getSystemService



class ShowMsgActivity : BaseActivity() {
    lateinit var mMediaPlayer:MediaPlayer;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_msg)
        TongJi()
        tongji_head_text.setText("time out")
        show_msg_tv.setText("I started exercising now")
//        var count = intent.getIntExtra("count",0)-1;
//        var sum = intent.getIntExtra("sum",0);
//        if(count>0){
//            var intent = Intent(this@ShowMsgActivity,ShowMsgActivity::class.java)
//            var time = System.currentTimeMillis()+10000;
//            intent.putExtra("title","第${sum-count-1}个番茄时间到")
//            intent.putExtra("count",count)
//            intent.putExtra("sum",sum)
//            AlarmUtils.startAlarmActivity(this@ShowMsgActivity,time,intent);
//        }
        try {
            var mRing = RingtoneManager(this@ShowMsgActivity)
            var mNumberOfRingtones = mRing.cursor.count
            var mRingToneUri = mRing.getRingtoneUri(0)
             mMediaPlayer = MediaPlayer()
            mMediaPlayer.setDataSource(this@ShowMsgActivity, mRingToneUri)
            mMediaPlayer.prepare()
            mMediaPlayer.setLooping(false)
            mMediaPlayer.start()

        } catch (ignore: Exception) {
        }


    }

    override fun onStop() {
        super.onStop()
        if (mMediaPlayer!=null && mMediaPlayer.isPlaying){
            mMediaPlayer.stop()
            mMediaPlayer.release()

        }

    }
}
