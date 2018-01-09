package com.michaelkatan.mathpower;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Leaderboard extends Activity {
    LinearLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_screen);

        lay = findViewById(R.id.lead_linear);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.orderByChild("_age");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Player p = new Player("", 0);
                ArrayList<Player> ppl = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    ppl.add(userSnapshot.getValue(Player.class));
                }

                for (int i = 0; i < ppl.size(); i++) {
                    TextView tempTV = new TextView(Leaderboard.this);
                    tempTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tempTV.setTextSize(25);
                    tempTV.setPadding(15, 15, 15, 15);
                    tempTV.setText(ppl.get(i).toString());
                    lay.addView(tempTV);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }
}
