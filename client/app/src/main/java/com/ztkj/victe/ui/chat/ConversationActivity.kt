package com.ztkj.victe.ui.chat

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.ztkj.victe.R
import kotlinx.android.synthetic.main.activity_conversation.*

class ConversationActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        chat_title.text = "${intent.getStringExtra("username")}"
    }
}
