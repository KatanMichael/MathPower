package com.michaelkatan.PowerMath;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    static final int USERNAME_RQST = 2;
    static final int GAMEMANAGER = 1;

    ActionBar bar;
    Window window;

    Player player = null;

    TextView welcome_tv;
    TextView fetching_TV;

    Button play_btn;
    Button setting_btn;
    Button leader_btn;
    Button practice_btn;

    DatabaseReference myRef;
    FirebaseDatabase dataBase;
    FirebaseAuth myAuth;
    FirebaseUser user;
    ChildEventListener childEventListener;

    LottieAnimationView fetching_anim;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        play_btn = findViewById(R.id.play_btn);
        setting_btn = findViewById(R.id.settings_btn);
        leader_btn = findViewById(R.id.leaderboard_btn);
        practice_btn = findViewById(R.id.practice_btn);

        welcome_tv = findViewById(R.id.mainText);
        fetching_TV = findViewById(R.id.main_fetching_data_TV);

        play_btn.setClickable(false);
        setting_btn.setClickable(false);
        leader_btn.setClickable(false);
        practice_btn.setClickable(false);

        dataBase = FirebaseDatabase.getInstance();
        myAuth = FirebaseAuth.getInstance();

        fetching_anim = findViewById(R.id.main_fetching_anim);
        fetching_anim.setAnimation("material_loading_2.json");
        fetching_anim.loop(true);
        fetching_anim.setVisibility(View.VISIBLE);
        fetching_anim.playAnimation();

        myRef = dataBase.getReference();
        user = myAuth.getCurrentUser();

        playerInDataBase();

//        if(player == null)
//        {
//            player = new Player(user.getEmail(),user.getUid());
//            myRef.child("users").child(""+user.getUid()).setValue(player);


        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                player.set_score(player.get_score() + 1);
//                myRef.child("users").child("" + user.getUid()).setValue(player);

                Intent intent = new Intent(MainActivity.this, GameManager.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, GAMEMANAGER);

            }
        });


        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);


        leader_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Leaderboard.class);
                startActivity(intent);
            }
        });


    }

    public void changeStatusBarColor(int color)
    {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(color));
        }

    }


    public void playerInDataBase() {
        childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ArrayList<Player> users;
                users = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                if (dataSnapshot != null) {
                    for (DataSnapshot child : children) {
                        users.add(child.getValue(Player.class));
                    }
                } else {
                    player = new Player(user.getEmail(), user.getUid());
                    myRef.child("users").child("" + user.getUid()).setValue(player);
                    restoreBtns();

                }

                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).get_id().equals(user.getUid())) {
                        player = users.get(i);
                        i = users.size() + 1;
                    }
                }

                if (player == null) {
                    player = new Player(user.getEmail(), user.getUid());
                    myRef.child("users").child("" + user.getUid()).setValue(player);
                    restoreBtns();

                }
                if ((player.get_name().equals(""))) {
                    Intent intent = new Intent(MainActivity.this, Username_Input.class);

                    startActivityForResult(intent, USERNAME_RQST);


                }
                restoreBtns();
                fetching_anim.cancelAnimation();
                fetching_anim.setVisibility(View.GONE);
                fetching_TV.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        myRef.addChildEventListener(childEventListener);

    }

    protected void onStop() {
        super.onStop();

        myRef.child("users").child("" + user.getUid()).setValue(player);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myAuth.signOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int score = data.getExtras().getInt("score");

                if (score > player.get_score()) {
                    Toast.makeText(this, "Your Broke Your Last Record by " + (score - player.get_score()) + " Points!", Toast.LENGTH_SHORT).show();
                    player.set_score(score);
                    myRef.child("users").child("" + user.getUid()).setValue(player);
                }

            }
        }


        if (requestCode == USERNAME_RQST) {
            if (resultCode == RESULT_OK) {
                String temp = data.getExtras().getString("username");
                player.set_name(temp);
                myRef.child("users").child("" + user.getUid()).setValue(player);
            }
        }


    }

    public void restoreBtns() {

        play_btn.setClickable(true);
        setting_btn.setClickable(true);
        leader_btn.setClickable(true);
        practice_btn.setClickable(true);
    }
}
