package com.example.myapplication.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.R
import com.example.myapplication.repository.ApiServiceRepository
import com.example.myapplication.repository.FirebaseAuthServiceRepository
import com.example.myapplication.repository.FirebaseServiceRepository
import com.example.myapplication.views.logInActivity.LogInActivity
import com.example.myapplication.views.logInActivity.SHARED_PREF_FILE

private lateinit var sharedPref: SharedPreferences

private const val TAG = "SplashActivity"

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val splashLong: Long = 5000 // five seconds
    internal val mRunnable: Runnable = Runnable {
        // for count the time
        if (!isFinishing) {
            sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
            if (sharedPref.getBoolean("state login", false)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Log.d(TAG, sharedPref.getBoolean("state login", true).toString())
            } else {
                val intent = Intent(applicationContext, LogInActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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