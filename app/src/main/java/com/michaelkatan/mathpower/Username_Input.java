package com.michaelkatan.mathpower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MichaelKatan on 15/01/2018.
 */

public class Username_Input extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_input);

        EditText editText = findViewById(R.id.username_et);
        Button button = findViewById(R.id.username_submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.username_et);

                if (editText.getText().toString().equals("")) {
                    Toast.makeText(Username_Input.this, "Enter Username Please", Toast.LENGTH_SHORT).show();
                } else {
                    String temp = editText.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("username", temp);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }
}

