package com.example.my

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //模拟下载进度
        ValueAnimator.ofFloat(0f,1f).apply {
            duration = 3000
            addUpdateListener {
                val value = it.animatedValue as Float
                circle_progress.load = value

            }
        }.start()
    }
}