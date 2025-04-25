package com.ztkj.victe.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.kelin.translucentbar.library.TranslucentBarManager
import com.ztkj.victe.R

open class BaseActivity : FragmentActivity() {

    private var parentLinearLayout: LinearLayout? = null// 把父类activity和子类activity的view都add到这里
    private var tongjiHeadLinear: RelativeLayout? = null
    private var tongjiBackLinear: LinearLayout? = null
    private var tongjiBackButton: TextView? = null
    var tongjiHeadText: TextView? = null
    var tongjiSaveLinear: LinearLayout? = null
    var tongjiSaveButton: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val translucentBarManager = TranslucentBarManager(this)
        translucentBarManager.transparent(this)
        initContentView(R.layout.activity_base)

    }

    private fun initContentView(layoutResID: Int) {
        val viewGroup = findViewById<View>(android.R.id.content) as ViewGroup
        viewGroup.removeAllViews()
        parentLinearLayout = LinearLayout(this)
        parentLinearLayout!!.setOrientation(LinearLayout.VERTICAL)
        viewGroup.addView(parentLinearLayout)
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true)
    }

    override  fun setContentView(layoutResID: Int) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true)
    }

    override  fun setContentView(view: View) {
        parentLinearLayout!!.addView(view)
    }

    override  fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        parentLinearLayout!!.addView(view, params)
    }

    open fun TongJi() {
        tongjiHeadLinear = findViewById(R.id.tongji_head_linear) as RelativeLayout
        tongjiBackLinear = findViewById(R.id.tongji_back_linear) as LinearLayout
        tongjiBackButton = findViewById(R.id.tongji_back_button) as TextView
        tongjiHeadText = findViewById(R.id.tongji_head_text) as TextView
        tongjiSaveLinear = findViewById(R.id.tongji_save_linear) as LinearLayout
        tongjiSaveButton = findViewById(R.id.tongji_save_button) as TextView
        tongjiHeadLinear!!.setVisibility(View.VISIBLE)
        initStatues()
        TongJiClick()
    }

    private fun initStatues() {
        if (getStatuesHeight() > 0) {
            val layoutParams = tongjiHeadLinear!!.getLayoutParams()
            layoutParams.height = layoutParams.height + getStatuesHeight()
            tongjiHeadLinear!!.setLayoutParams(layoutParams)
            tongjiHeadLinear!!.setPadding(0, getStatuesHeight(), 0, 0)
        }
    }

    protected fun getStatuesHeight(): Int {
        var statusBarHeight1 = -1
        val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight1 = this.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight1
    }

    private fun TongJiClick() {

        tongjiBackButton!!.setOnClickListener(View.OnClickListener {

            finish()

        })

        tongjiBackLinear!!.setOnClickListener(View.OnClickListener {

            finish()

        })

    }

    //toast消息
    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
