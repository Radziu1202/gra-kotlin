package com.example.myapplicationn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class GameMain : AppCompatActivity() {
    var ShareButtonLayout: RelativeLayout? = null
    var game: FrameLayout? = null
    var gmp: GamePanel? = null
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        Constants.SCREEN_WIDTH = dm.widthPixels
        Constants.SCREEN_HEIGHT = dm.heightPixels
        setContentView(R.layout.activity_game_main)
        gmp = GamePanel(this)
        game = FrameLayout(this)
        ShareButtonLayout = RelativeLayout(this)
        val share_button = Button(this)
        share_button.text = "share score"
        share_button.id = 20000
        share_button.setOnClickListener { shareResult() }
        val button = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        ShareButtonLayout!!.layoutParams = params
        ShareButtonLayout!!.addView(share_button)
        button.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
        button.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
        share_button.layoutParams = button
        game!!.addView(gmp)
        game!!.addView(ShareButtonLayout)
        setContentView(game)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
        //    System.exit(1);  //DO POPRAWY
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun shareResult() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                gmp!!.manager!!.scenes[0].getObstacleManager()!!.getScore()
            )
        if (Constants.CURRENT_CONTEXT?.let { shareIntent.resolveActivity(it.getPackageManager()) } != null) {
            startActivity(shareIntent)
        }
    }
}