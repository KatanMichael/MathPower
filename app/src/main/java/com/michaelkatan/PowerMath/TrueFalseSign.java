package com.michaelkatan.PowerMath;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MichaelKatan on 18/01/2018.
 */

public class TrueFalseSign extends Activity {
    ActionBar bar;
    Window window;


    int totalScore;
    int totalQuastions;

    int leftSum = 0;
    int rightSum = 0;

    TextView sign_score_TV;
    TextView sign_leftOP_TV;
    TextView sign_rightOP_TV;
    TextView sign_mid_TV;


    ImageView[] hearts;
    int counterHearts = 0;

    Button sign_true_btn;
    Button sign_false_btn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.true_or_false_sign);



        sign_score_TV = findViewById(R.id.sign_score_TV);
        sign_leftOP_TV = findViewById(R.id.sign_leftOp);
        sign_rightOP_TV = findViewById(R.id.sign_rightOp);
        sign_mid_TV = findViewById(R.id.sign_signTV);

        sign_true_btn = findViewById(R.id.sign_true);
        sign_false_btn = findViewById(R.id.sign_false);


        hearts = new ImageView[3];
        hearts[0] = findViewById(R.id.trueFalse_heart1);
        hearts[1] = findViewById(R.id.trueFalse_heart2);
        hearts[2] = findViewById(R.id.trueFalse_heart3);


        updateVariables();
        updateHearts();

        sign_score_TV.setText("Score: " + totalScore + " / " + totalQuastions);
        setUpQuestion();
        setUpQuestion();
        setUpMidSign();


        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);

        sign_false_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean choseRight = false;
                String sign = sign_mid_TV.getText().toString();
                int diff;
                diff = leftSum - rightSum;

                if ((diff < 0) && sign.equals("<")) {
                    Toast.makeText(TrueFalseSign.this, "Right", Toast.LENGTH_SHORT).show();
                    choseRight = true;
                    setResult(RESULT_OK);
                    finish();

                }
                if ((diff > 0) && sign.equals(">")) {
                    Toast.makeText(TrueFalseSign.this, "Right", Toast.LENGTH_SHORT).show();
                    choseRight = true;
                    setResult(RESULT_OK);
                    finish();

                }

                if (!choseRight) {
                    Toast.makeText(TrueFalseSign.this, "Wrong", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    finish();

                }

            }
        });

        sign_true_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean choseRight = false;
                String sign = sign_mid_TV.getText().toString();
                int diff;
                diff = leftSum - rightSum;

                if ((diff < 0) && sign.equals(">")) {
                    Toast.makeText(TrueFalseSign.this, "Right", Toast.LENGTH_SHORT).show();
                    choseRight = true;
                    setResult(RESULT_OK);
                    finish();
                }
                if ((diff > 0) && sign.equals("<")) {
                    Toast.makeText(TrueFalseSign.this, "Right", Toast.LENGTH_SHORT).show();
                    choseRight = true;
                    setResult(RESULT_OK);
                    finish();

                }

                if (!choseRight) {
                    Toast.makeText(TrueFalseSign.this, "Wrong", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    finish();

                }
            }
        });

    }

    private void updateHearts() {
        if (counterHearts == 2) {
            hearts[2].setVisibility(View.GONE);
        }

        if (counterHearts == 1) {
            hearts[2].setVisibility(View.GONE);
            hearts[1].setVisibility(View.GONE);
        }
    }


    private void updateVariables() {

        int temp;
        temp = getIntent().getExtras().getInt("score");
        totalScore = temp;
        temp = getIntent().getExtras().getInt("total");
        totalQuastions = temp;
        temp = getIntent().getExtras().getInt("lives");
        counterHearts = temp;

    }

    private void setUpMidSign() {
        int temp = (int) ((Math.random() * 10) % 2);

        if (temp == 1) {
            sign_mid_TV.setText("<");
        } else {
            sign_mid_TV.setText(">");
        }
    }

    private void setUpQuestion() {
        int temp;
        int x;
        int y;

        if (leftSum == 0) {
            x = (int) (Math.random() * 100);
            y = (int) (Math.random() * 100);
            temp = (int) ((Math.random() * 10) % 2);

            if (temp == 1) {
                leftSum = x - y;
                sign_leftOP_TV.setText("" + x + " - " + y);
            } else {
                leftSum = x - y;
                sign_leftOP_TV.setText("" + x + " + " + y);

            }

        } else {
            x = (int) (Math.random() * 100);
            y = (int) (Math.random() * 100);
            temp = (int) ((Math.random() * 10) % 2);

            if (temp == 1) {
                leftSum = x - y;
                sign_rightOP_TV.setText("" + x + " - " + y);
            } else {
                leftSum = x - y;
                sign_rightOP_TV.setText("" + x + " + " + y);

            }

        }

    }

    public void changeStatusBarColor(int color) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(color));
        }

    }

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onStop() {
        super.onStop();
        //  overridePendingTransition(0,0);
    }
}
