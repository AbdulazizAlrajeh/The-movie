package com.example.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myapplication.R

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val splashLong: Long = 5000 // five seconds
    internal val mRunnable: Runnable = Runnable {
        // for count the time
        if (!isFinishing) {

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.navigationBarColor = this.resources.getColor(R.color.background)
        mDelayHandler = Handler()

        mDelayHandler!!.postDelayed(mRunnable, splashLong)
    }


    override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}