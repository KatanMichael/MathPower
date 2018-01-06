package com.michaelkatan.mathpower;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MichaelKatan on 06/01/2018.
 */

public class FirstScreen extends Activity {

    ActionBar bar;
    Window window;

    EditText first_name_et;
    EditText first_age_et;
    Button first_enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        first_age_et = findViewById(R.id.first_age);
        first_name_et = findViewById(R.id.first_et_name);
        first_enter = findViewById(R.id.first_enter_btn);

        first_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = first_name_et.getText().toString();
                if (!name.equals("")) {
                    if (!(first_age_et.getText().toString().equals(""))) {
                        int age = Integer.parseInt(first_age_et.getText().toString());

                        Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                        intent.putExtra("age", age);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(FirstScreen.this, "Enter Your Age Please", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(FirstScreen.this, "Enter Your Name Please", Toast.LENGTH_SHORT).show();
                    //TODO Add to string.xml
                }


            }
        });


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
