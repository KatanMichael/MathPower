package com.michaelkatan.mathpower;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity
{
    ActionBar bar;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);


    }

    public void changeStatusBarColor(int color)
    {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(color));

    }
}
