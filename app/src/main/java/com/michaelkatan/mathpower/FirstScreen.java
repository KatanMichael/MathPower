package com.michaelkatan.mathpower;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by MichaelKatan on 06/01/2018.
 */

public class FirstScreen extends Activity {

    ActionBar bar;
    Window window;

    EditText first_email_et;
    EditText first_pass_et;
    Button first_signUp;
    Button first_signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        first_pass_et = findViewById(R.id.first_pass);
        first_email_et = findViewById(R.id.first_et_email);
        first_signUp = findViewById(R.id.first_signup_btn);
        first_signIn = findViewById(R.id.first_signin_btn);






        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);


    }


    public void changeStatusBarColor(int color) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(color));
        }

    }
}
