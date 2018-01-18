package com.michaelkatan.PowerMath;

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

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        first_pass_et = findViewById(R.id.first_pass);
        first_email_et = findViewById(R.id.first_et_email);
        first_signUp = findViewById(R.id.first_signup_btn);
        first_signIn = findViewById(R.id.first_signin_btn);

        myAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);

        first_email_et.setText(sharedPreferences.getString("email", ""));


        /*
            Firebase Signup System,
         */
        first_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                            } else {
                                Log.w("Signup", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(FirstScreen.this, "" + task.getException(),
                                        Toast.LENGTH_SHORT).show();
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

                                Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                                intent.putExtra("email", user.getEmail());
                                intent.putExtra("ID", user.getUid());


                                startActivity(intent);


                            } else {
                                Log.w("Signin", "signInWithEmail:failure", task.getException());
                                Toast.makeText(FirstScreen.this, "" + task.getException(),
                                        Toast.LENGTH_SHORT).show();

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
