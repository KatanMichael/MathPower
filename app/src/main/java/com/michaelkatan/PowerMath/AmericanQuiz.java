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

    boolean right = false;

    TextView quastionTV;
    TextView answerTV;
    TextView scoreTv;

    ImageView[] hearts;
    int counterHearts = 0;


    Button sumbit_btn;
    Button btn_A;
    Button btn_B;
    Button btn_C;
    Button btn_D;

    int count = 0;


    ArrayList<Button> answerBtns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.american_quiz);

        hearts=new ImageView[3];

        hearts[0] = findViewById(R.id.american_heart1);
        hearts[1] = findViewById(R.id.american_heart2);
        hearts[2] = findViewById(R.id.american_heart3);


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

        updateVariables();
        updateHearts();


        scoreTv.setText("Score: " + rightAnswers + " / " + totalQuastions);
        getRandomQuastion();
        getRandomAnswers();


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
                        finishGame();
                    } else {
                        right = false;
                        finishGame();
                    }

                    if (count != 3) {
                        answerTV.setText("?");
                        scoreTv.setText(rightAnswers + " / " + totalQuastions);
                    }
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
        rightAnswers = temp;
        temp = getIntent().getExtras().getInt("total");
        totalQuastions = temp;
        temp = getIntent().getExtras().getInt("lives");
        counterHearts = temp;
    }


    private void finishGame() {

        if (right) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
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
        super.onStop();
        overridePendingTransition(0, 0);
    }

    public class myClickListener implements View.OnClickListener {
        Button b;

        @Override
        public void onClick(View v) {
            b = (Button) v;
            answerTV.setText(b.getText().toString());


        }
    }
}
