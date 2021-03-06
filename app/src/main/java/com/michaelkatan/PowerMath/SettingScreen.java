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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MichaelKatan on 05/02/2018.
 */

public class SettingScreen extends Activity {
    ActionBar bar;
    Window window;

    EditText inputName;
    Button changeName_btn;

    TextView email_tv;
    TextView username_tv;
    TextView highScore_tv;

    Switch musicSwtich;
    Switch effectSwitch;
    Switch easyMode;

    boolean musicOn = true;
    boolean effectsOn = true;
    boolean nameChanged = false;
    boolean easyModeOn = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);


        inputName = findViewById(R.id.setting_nameET);
        changeName_btn = findViewById(R.id.settings_nickname_btn);

        username_tv = findViewById(R.id.setting_username_tv);
        email_tv = findViewById(R.id.setting_email_tv);
        highScore_tv = findViewById(R.id.setting_highscore_tv);

        musicSwtich = findViewById(R.id.setting_music_switch);
        effectSwitch = findViewById(R.id.setting_effects_switch);
        easyMode = findViewById(R.id.setting_easymode_switch);


        musicSwtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                musicOn = isChecked;
            }
        });

        effectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                effectsOn = isChecked;
            }
        });

        easyMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                easyModeOn = isChecked;

            }
        });

        final String usernameString = getString(R.string.username);
        final String emailString=getString(R.string.email);
        final String HighscoreString=getString(R.string.CurrentHighScore);

        updateVariables();



        changeName_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameChanged = true;
                Toast.makeText(SettingScreen.this, "Nickname Changed", Toast.LENGTH_SHORT).show();
                username_tv.setText(usernameString + inputName.getText().toString());
                //TODO Add to string.xml and translate
            }
        });



        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);

    }

    public void updateVariables() {

        final String usernameString = getString(R.string.username);
        final String emailString=getString(R.string.email);
        final String HighscoreString=getString(R.string.CurrentHighScore);

        String temp;
        int tempInt;
        temp = getIntent().getExtras().getString("username", " ");
        inputName.setText(temp);

        tempInt = getIntent().getExtras().getInt("score");
        highScore_tv.setText(HighscoreString + tempInt);

        temp = getIntent().getExtras().getString("username");
        username_tv.setText(usernameString + temp);

        temp = getIntent().getExtras().getString("email");
        email_tv.setText(emailString + temp);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("music", musicOn);
        intent.putExtra("effects", effectsOn);
        intent.putExtra("easyMode", easyModeOn);

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
