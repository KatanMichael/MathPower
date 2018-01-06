package com.michaelkatan.mathpower;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{
    ActionBar bar;
    Window window;


    TextView welcome_tv;
    Button play_btn;
    Button setting_btn;
    Button leader_btn;
    Button practice_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_btn = findViewById(R.id.play_btn);
        setting_btn = findViewById(R.id.settings_btn);
        leader_btn = findViewById(R.id.leaderboard_btn);
        practice_btn = findViewById(R.id.practice_btn);
        welcome_tv = findViewById(R.id.mainText);


        int age = getIntent().getIntExtra("age", 0);
        String name = getIntent().getStringExtra("name");

        Player player = new Player(name, age);
        welcome_tv.setText("Hello And Welcome \n" + name);
        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);


    }

    public void changeStatusBarColor(int color)
    {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(color));
        }

    }
}
