package com.michaelkatan.PowerMath;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MichaelKatan on 05/02/2018.
 */

public class SettingScreen extends Activity {
    ActionBar bar;
    Window window;

    EditText inputName;
    Button changeName_btn;

    Boolean nameChanged = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);

        inputName = findViewById(R.id.setting_nameET);
        changeName_btn = findViewById(R.id.settings_nickname_btn);

        updateVariables();

        changeName_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameChanged = true;
                Toast.makeText(SettingScreen.this, "Nickname Changed", Toast.LENGTH_SHORT).show();
                //TODO Add to string.xml and translate
            }
        });


        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);

    }

    private void updateVariables() {
        String temp;
        temp = getIntent().getExtras().getString("username", " ");
        inputName.setText(temp);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (nameChanged) {
            intent.putExtra("name", inputName.getText().toString());
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }

        finish();
    }

    public void changeStatusBarColor(int color) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(color));
        }
    }

}
