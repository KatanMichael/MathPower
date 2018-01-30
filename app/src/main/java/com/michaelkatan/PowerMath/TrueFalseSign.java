package com.michaelkatan.PowerMath;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    long time;
    long timeLeft = 0;

    TextView sign_timerTV;
    TextView sign_score_TV;
    TextView sign_leftOP_TV;
    TextView sign_rightOP_TV;
    TextView sign_mid_TV;


    ImageView[] hearts;
    int counterHearts = 0;

    int rightAnswersInRow=0;

    Button sign_true_btn;
    Button sign_false_btn;

    ImageView starAnim;
    ImageView fiveRowAnim;
    ImageView tenRowAnim;

    MyTimer timer;

    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.true_or_false_sign);


        starAnim=findViewById(R.id.star_anim);
        fiveRowAnim=findViewById(R.id.five_row);
        tenRowAnim=findViewById(R.id.ten_row);


        sign_score_TV = findViewById(R.id.sign_score_TV);
        sign_leftOP_TV = findViewById(R.id.sign_leftOp);
        sign_rightOP_TV = findViewById(R.id.sign_rightOp);
        sign_mid_TV = findViewById(R.id.sign_signTV);
        sign_timerTV = findViewById(R.id.sign_timer);

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

        timer = new MyTimer(time, 1000);
        timer.start();

        sign_false_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("time", timeLeft);

                timer.cancel();
                boolean choseRight = false;
                String sign = sign_mid_TV.getText().toString();
                int diff;
                diff = leftSum - rightSum;

                if ((diff < 0) && sign.equals("<")) {
                    //Toast.makeText(TrueFalseSign.this, "Right", Toast.LENGTH_SHORT).show();
                    choseRight = true;
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);


                }
                if ((diff > 0) && sign.equals(">")) {
                    //  Toast.makeText(TrueFalseSign.this, "Right", Toast.LENGTH_SHORT).show();
                    choseRight = true;
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }

                if (!choseRight) {
                    // Toast.makeText(TrueFalseSign.this, "Wrong", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }

            }
        });

        sign_true_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("time", timeLeft);

                timer.cancel();
                boolean choseRight = false;
                String sign = sign_mid_TV.getText().toString();
                int diff;
                diff = leftSum - rightSum;

                if ((diff < 0) && sign.equals(">")) {
                    //Toast.makeText(TrueFalseSign.this, "Right", Toast.LENGTH_SHORT).show();
                    choseRight = true;
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }
                if ((diff > 0) && sign.equals("<")) {
                    // Toast.makeText(TrueFalseSign.this, "Right", Toast.LENGTH_SHORT).show();
                    choseRight = true;
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);


                }

                if (!choseRight) {
                    //Toast.makeText(TrueFalseSign.this, "Wrong", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }
            }
        });

    }


    private void animationManager()
    {
        if(rightAnswersInRow>=5)
        {
            if (rightAnswersInRow % 15 == 0 ) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_and_fade);
                starAnim.startAnimation(animation1);
            } else if (rightAnswersInRow % 10 == 0) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_and_fade);
                tenRowAnim.startAnimation(animation1);
            } else if (rightAnswersInRow % 5 == 0 ) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_and_fade);
                fiveRowAnim.startAnimation(animation1);
            }
        }
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
        long timeTemp;
        temp = getIntent().getExtras().getInt("score");
        totalScore = temp;
        temp = getIntent().getExtras().getInt("total");
        totalQuastions = temp;
        temp = getIntent().getExtras().getInt("lives");
        counterHearts = temp;
        temp = getIntent().getExtras().getInt("rightAnswersInRow");
        rightAnswersInRow = temp;
        timeTemp = getIntent().getExtras().getLong("time");

        time = timeTemp;


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

        overridePendingTransition(0, 0);
        super.onStop();
    }

    public class MyTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sign_timerTV.setText("Time: " + millisUntilFinished / 1000 + " Sec");
            timeLeft = millisUntilFinished;
        }

        @Override
        public void onFinish() {
            Toast.makeText(TrueFalseSign.this, "Finish", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("time", 0);
            setResult(RESULT_CANCELED, intent);
            finish();


        }


    }
}
