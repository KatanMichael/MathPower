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

import java.util.ArrayList;

/**
 * Created by MichaelKatan on 14/01/2018.
 */

public class AmericanQuiz extends Activity {

    ActionBar bar;
    Window window;

    int num1;
    int num2;
    int answer;
    char sign;
    int totalQuastions = 0;
    int rightAnswers = 0;
    int rightAnswersInRow=0;

    long time;
    long timeLeft;


    boolean right = false;

    TextView quastionTV;
    TextView answerTV;
    TextView scoreTv;
    TextView timeTv;

    ImageView[] hearts;
    int counterHearts = 0;


    Button sumbit_btn;
    Button btn_A;
    Button btn_B;
    Button btn_C;
    Button btn_D;
    Button btn_test;
    int count = 0;

    ImageView starAnim;
    ImageView fiveRowAnim;
    ImageView tenRowAnim;


    ArrayList<Button> answerBtns;

    MyTimer timer;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.american_quiz);

        btn_test=findViewById(R.id.test_button);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswersInRow++;
                rightAnswers++;
                scoreTv.setText("Score: " + rightAnswers + " / " + totalQuastions);
            }
        });

        starAnim=findViewById(R.id.star_anim);
        fiveRowAnim=findViewById(R.id.five_row);
        tenRowAnim=findViewById(R.id.ten_row);

        hearts=new ImageView[3];
        hearts[0] = findViewById(R.id.american_heart1);
        hearts[1] = findViewById(R.id.american_heart2);
        hearts[2] = findViewById(R.id.american_heart3);


        timeTv = findViewById(R.id.american_timer);
        quastionTV = findViewById(R.id.quastion_TV);
        answerTV = findViewById(R.id.asnwer);
        scoreTv = findViewById(R.id.score_TV);

        sumbit_btn = findViewById(R.id.submit_btn);
        btn_A = findViewById(R.id.btn_A);
        btn_B = findViewById(R.id.btn_B);
        btn_C = findViewById(R.id.btn_C);
        btn_D = findViewById(R.id.btn_D);


        answerBtns = new ArrayList<>();
        answerBtns.add(btn_A);
        answerBtns.add(btn_B);
        answerBtns.add(btn_C);
        answerBtns.add(btn_D);


        btn_A.setOnClickListener(new myClickListener());
        btn_B.setOnClickListener(new myClickListener());
        btn_C.setOnClickListener(new myClickListener());
        btn_D.setOnClickListener(new myClickListener());

        sharedPreferences = getSharedPreferences("timeLeft", MODE_PRIVATE);

        updateVariables();
        updateHearts();
        Toast.makeText(this, "" + time, Toast.LENGTH_SHORT).show();
        timer = new MyTimer(time, 1000);
        timer.start();

        scoreTv.setText("Score: " + rightAnswers + " / " + totalQuastions);
        getRandomQuastion();
        getRandomAnswers();
        animationManger();

        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);

        sumbit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerTV.getText().toString().equals("_____________________")) {
                    Toast.makeText(AmericanQuiz.this, "Select Answer First..", Toast.LENGTH_SHORT).show();

                } else {

                    int temp;
                    temp = Integer.parseInt(answerTV.getText().toString());
                    if (temp == answer) {
                        Toast.makeText(AmericanQuiz.this, "Your Right!", Toast.LENGTH_SHORT).show();
                        right = true;
                        rightAnswersInRow++;
                        finishGame();
                    } else {
                        right = false;
                        rightAnswersInRow=0;
                        finishGame();
                    }

                    if (count != 3) {
                        answerTV.setText("?");
                        scoreTv.setText(rightAnswers + " / " + totalQuastions);
                    }
                }
                if(rightAnswersInRow>=5)
                {
                    animationManger();
                }
            }
        });


    }

    public void animationManger()
    {
        if (rightAnswers >= 5) {
            if (rightAnswers % 15 == 0) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_and_fade);
                starAnim.startAnimation(animation1);
            } else if (rightAnswers % 10 == 0) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_and_fade);
                tenRowAnim.startAnimation(animation1);
            } else if (rightAnswers % 5 == 0) {
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
        temp = getIntent().getExtras().getInt("score");
        rightAnswers = temp;
        temp = getIntent().getExtras().getInt("total");
        totalQuastions = temp;
        temp = getIntent().getExtras().getInt("lives");
        counterHearts = temp;
        time = sharedPreferences.getLong("time", 30000);

        if (time == 0) {
            time = 30000;
        }
    }


    private void finishGame() {
        Intent intent = new Intent();
        intent.putExtra("timeLeft", timeLeft);
        if (right) {
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }
        timer.cancel();
        finish();
        overridePendingTransition(0, 0);
    }

    private void getRandomQuastion() {
        num1 = (int) (Math.random() * 100);
        num2 = (int) (Math.random() * 100);

        if ((int) ((Math.random() * 10) % 2) == 0) {
            sign = '+';
            answer = num1 + num2;
        } else {
            sign = '-';
            answer = num1 - num2;
        }

        quastionTV.setText(num1 + " " + sign + " " + num2 + " = ?");

    }

    private void getRandomAnswers() {
        int rightAnswer, closeAnswer, margin;
        int temp;
        rightAnswer = (int) (Math.random() * 10) % 4;
        closeAnswer = (int) (Math.random() * 10) % 4;

        margin = (int) (Math.random() * 10) % 6;

        while (margin == 0) {
            margin = (int) (Math.random() * 10) % 6;
        }

        if ((int) (Math.random() * 10) % 2 == 0) {
            margin = margin * -1;
        }

        while (closeAnswer == rightAnswer) {
            closeAnswer = (int) (Math.random() * 10) % 4;
        }

        answerBtns.get(rightAnswer).setText(answer + "");
        answerBtns.get(closeAnswer).setText(answer + margin + "");

        for (int i = 0; i < 4; i++) {
            if (i != rightAnswer && i != closeAnswer) {
                temp = (int) (Math.random() * 100);
                if (answer < 0) {
                    temp = temp * -1;
                }

                answerBtns.get(i).setText(temp + "");
            }
        }

    }

    @Override
    public void onBackPressed() {

    }

    public void changeStatusBarColor(int color) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(color));
        }

    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("time", timeLeft);
        editor.commit();


        timer.cancel();
        overridePendingTransition(0, 0);
        super.onStop();
    }

    public class myClickListener implements View.OnClickListener {
        Button b;

        @Override
        public void onClick(View v) {
            b = (Button) v;
            answerTV.setText(b.getText().toString());


        }
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
            //TODO Add String Transtiolns To Timer in all activities
            timeTv.setText("Time: " + millisUntilFinished / 1000 + " Sec");
            timeLeft = millisUntilFinished;

        }

        @Override
        public void onFinish() {
            Toast.makeText(AmericanQuiz.this, "Finish", Toast.LENGTH_SHORT).show();
        }
    }
}
