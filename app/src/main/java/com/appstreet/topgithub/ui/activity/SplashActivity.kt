package com.appstreet.topgithub.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.appstreet.topgithub.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setBounceAnimation()
        Handler().postDelayed(Runnable {
            callMainActivity()
        },2000)
    }

    private fun setBounceAnimation(){
        val startAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        ivLogo.startAnimation(startAnimation)
    }

    /**
     * Call Main activity and finish Splash activity
     */
    private fun callMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
