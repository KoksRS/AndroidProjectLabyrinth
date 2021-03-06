package com.artem_tereshko.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Artem Tereshko on 05/12/2014.
 * AndroidProject
 */
public class WinActivity extends Activity {

    int nextLevel =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
        nextLevel = intent.getIntExtra("level", 1);

        setContentView(R.layout.win_layout);


    }


    public void button_next_level_clicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level", nextLevel);
        startActivity(intent);
    }

    public void button_menu_clicked(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
