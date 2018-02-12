package com.michaelkatan.PowerMath;

import android.animation.Animator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    String email;
    String pass;

    LottieAnimationView loadingAnim;
    LottieAnimationView happyAnim;
    LottieAnimationView chackedgAnim;
    LottieAnimationView warning_sign;
    Boolean test = true;

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        first_pass_et = findViewById(R.id.first_pass);
        first_email_et = findViewById(R.id.first_et_email);
        first_signUp = findViewById(R.id.first_signup_btn);
        first_signIn = findViewById(R.id.first_signin_btn);

        loadingAnim = findViewById(R.id.first_loadingAnim_view);
        loadingAnim.setAnimation("loading.json");
        loadingAnim.loop(true);
        loadingAnim.setVisibility(View.GONE);
        loadingAnim.useHardwareAcceleration();

        warning_sign=findViewById(R.id.first_warning_view);
        warning_sign.loop(false);


        chackedgAnim = findViewById(R.id.first_ChackAnim_view);
        chackedgAnim.setVisibility(View.GONE);
        loadingAnim.loop(false);


        happyAnim = findViewById(R.id.first_happyAnim_view);
        happyAnim.useExperimentalHardwareAcceleration(false);


        myAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);

        first_email_et.setText(sharedPreferences.getString("email", ""));


        /*
            Firebase Signup System,
         */
        first_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAnim.setVisibility(View.VISIBLE);
                loadingAnim.playAnimation();
                first_signUp.setClickable(false);
                email = first_email_et.getText().toString();
                pass = first_pass_et.getText().toString();

                if (!(email.equals("")) && !(pass.equals(""))) {
                    myAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Signup", "createUserWithEmail:success");

                                FirebaseUser user = myAuth.getCurrentUser();
                                Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                                intent.putExtra("email", user.getEmail());
                                intent.putExtra("ID", user.getUid());

                                startActivity(intent);
                                first_signIn.setClickable(true);
                                first_signUp.setClickable(true);

                                loadingAnim.pauseAnimation();
                                loadingAnim.setVisibility(View.GONE);
                                first_signUp.setClickable(true);

                            } else {
                                Log.w("Signup", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(FirstScreen.this, "" + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                loadingAnim.pauseAnimation();
                                loadingAnim.setVisibility(View.GONE);
                                first_signUp.setClickable(true);
                            }


                        }
                    });


                }


            }
        });


        /*
            Firebase signing System.

         */
        first_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAnim.setVisibility(View.VISIBLE);
                loadingAnim.playAnimation();
                first_signIn.setClickable(false);
                email = first_email_et.getText().toString();
                pass = first_pass_et.getText().toString();


                if (!(email.equals("")) && !(pass.equals(""))) {
                    myAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Signin", "signInWithEmail:success");
                                FirebaseUser user = myAuth.getCurrentUser();

                                final Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                                intent.putExtra("email", user.getEmail());
                                intent.putExtra("ID", user.getUid());

                                loadingAnim.cancelAnimation();

                                chackedgAnim.addAnimatorListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        startActivity(intent);
                                        first_signIn.setClickable(true);
                                        first_signUp.setClickable(true);
                                        chackedgAnim.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }
                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                });


                                chackedgAnim.playAnimation();



                            } else {
                                Log.w("Signin", "signInWithEmail:failure", task.getException());
                                Toast.makeText(FirstScreen.this, "" + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                loadingAnim.pauseAnimation();
                                loadingAnim.setVisibility(View.INVISIBLE);
                                warning_sign.setVisibility(View.VISIBLE);
                                warning_sign.playAnimation();
                                first_signIn.setClickable(true);
                            }


                        }
                    });
                }


            }
        });




        /*
            Hide the actionBar And Change the status bar color
         */
        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);


        happyAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                happyAnim.playAnimation();
            }
        });
        loadingAnim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                loadingAnim.setVisibility(View.GONE);
                chackedgAnim.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });


    }


    public void changeStatusBarColor(int color) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(color));
        }

    }


    /*
        Save the lase string that was enterd in the email edit text
     */
    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("email", first_email_et.getText().toString());
        editor.commit();

    }
}
