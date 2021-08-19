package com.example.aroundtheworld.Game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;

import androidx.annotation.ContentView;

import com.example.aroundtheworld.R;

public class GameMain  extends Activity {
    RelativeLayout ShareButtonLayout;
    FrameLayout game;
    GamePanel gmp;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            Constants.SCREEN_WIDTH=dm.widthPixels;
            Constants.SCREEN_HEIGHT=dm.heightPixels;
            setContentView(R.layout.activity_game_main);

            gmp= new GamePanel(this);
            game=new FrameLayout(this);
            ShareButtonLayout = new RelativeLayout(this);

            Button share_button = new Button(this );
            share_button.setText("share score");
            share_button.setId(20000);

            share_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    shareResult();
                                                }
                                            }

            );


            RelativeLayout.LayoutParams button=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);





            ShareButtonLayout.setLayoutParams(params);

            ShareButtonLayout.addView(share_button);

            button.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
            button.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);

            share_button.setLayoutParams(button);



            game.addView(gmp);
            game.addView(ShareButtonLayout);

            setContentView(game);
        }

    @Override
    public void onBackPressed() {
        this.finish();

        super.onBackPressed();
    //    System.exit(1);  //DO POPRAWY

    }

    @SuppressLint("QueryPermissionsNeeded")
    private void shareResult(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT,gmp.manager.scenes.get(0).getObstacleManager().getScore());
        if(shareIntent.resolveActivity(Constants.CURRENT_CONTEXT.getPackageManager())!=null){
            startActivity(shareIntent);
        }
    }

}
