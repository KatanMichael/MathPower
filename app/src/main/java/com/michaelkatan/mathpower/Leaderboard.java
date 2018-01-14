package com.michaelkatan.mathpower;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Leaderboard extends Activity {

    ArrayAdapter arrayAdapter;
    List<Player> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_screen);

        list = new ArrayList<>();
        listView = findViewById(R.id.lead_listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.orderByChild("_score");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Player> ppl = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    ppl.add(userSnapshot.getValue(Player.class));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ppl.sort(new PlayerComparator());
                }
                arrayAdapter.addAll(ppl);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Leaderboard.this, "" + databaseError.getDetails(), Toast.LENGTH_SHORT).show();

            }

        });
    }
}


//Private Inner Class to compere player and arrange them by score
class PlayerComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2) {

        if (p1.get_score() > p2.get_score()) {
            return -1;
        }

        if (p1.get_score() == p2.get_score()) {
            return 0;
        }

        return 1;


    }
}