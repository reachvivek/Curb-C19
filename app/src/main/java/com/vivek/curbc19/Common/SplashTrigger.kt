package com.vivek.curbc19.Common

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vivek.curbc19.R
import com.vivek.curbc19.User.UserDashboard

class SplashTrigger : AppCompatActivity() {
    lateinit var sp: Splash
    private var doubleBackToExitPressedOnce = false
    var onBoardingScreen: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        setSplash()
        setFullScreen(yes = true)
        setFullScreen()

    }

    private fun setFullScreen(yes: Boolean)  : SplashTrigger {
        intent.putExtra(SplashActivity.FULL_SCREEN, yes)
        return this
    }
    private fun setFullScreen() {

        if (intent.hasExtra(SplashActivity.FULL_SCREEN)) {

            if (intent.getBooleanExtra(SplashActivity.FULL_SCREEN, false))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = Color.TRANSPARENT
                    window.navigationBarColor = Color.TRANSPARENT
                    window.setFlags(
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    )
                }
        }

    }

    public fun getrandomanim(): Splash.Animation {
        val rnds = (1..4).random()
        return when (rnds) {
            1 -> Splash.Animation.GLOW_LOGO

            2 -> Splash.Animation.SLIDE_IN_LEFT_RIGHT

            3 -> Splash.Animation.SLIDE_LEFT_ENTER

            4 -> Splash.Animation.GLOW_LOGO_TITLE

            else -> Splash.Animation.GLOW_LOGO_TITLE
        }
    }

    private fun setSplash() {
        Splash(this)
                .setLogo(R.drawable.logo)
                .setAnimation(getrandomanim())
                .setBackgroundResource(R.color.white)
                .setTitleColor(R.color.htmlCadetBlue)
                .setProgressColor(R.color.materialPrimaryDarkBlueGrey)
                .setTitle(R.string.stitle)
                .setSubTitle(R.string.Splash_subtitle)
                .setFullScreen(true)
                .show()

        Splash.onComplete(object : Splash.OnComplete {
            override fun onComplete() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen", Context.MODE_PRIVATE)
                val isFirstTime = onBoardingScreen!!.getBoolean("firstTime", true)
                Toast.makeText(this@SplashTrigger, "Welcome", Toast.LENGTH_SHORT).show()
                if (isFirstTime) {
                    val editor = onBoardingScreen!!.edit()
                    editor.putBoolean("firstTime", false)
                    editor.commit()

                    startActivity(Intent(this@SplashTrigger, OnBoarding::class.java))
                    finish()
                }
                else {
                    startActivity(Intent(this@SplashTrigger, UserDashboard::class.java))
                    finish()
                }
            }

        })
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}
