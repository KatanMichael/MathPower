package com.michaelkatan.mathpower;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity
{
    ActionBar bar;
    Window window;


    TextView welcome_tv;
    Button play_btn;
    Button setting_btn;
    Button leader_btn;
    Button practice_btn;

    DatabaseReference myRef;
    FirebaseDatabase dataBase;

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

        dataBase = FirebaseDatabase.getInstance();


        int age = getIntent().getIntExtra("age", 0);
        String name = getIntent().getStringExtra("name");

        final Player player = new Player(name, age);
        welcome_tv.setText("Hello And Welcome \n" + name);
        bar = getActionBar();
        bar.hide();
        window = this.getWindow();
        changeStatusBarColor(R.color.colorPrimaryDark);

        myRef = dataBase.getReference(player.get_id() + "");

        myRef.setValue(player);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
                DatabaseReference tempRef = dataBase.getReference(myRef.getKey());
                tempRef.setValue(player);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

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

    // Read from the database

}
