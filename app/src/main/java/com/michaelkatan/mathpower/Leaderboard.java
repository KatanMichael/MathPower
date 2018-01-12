package com.michaelkatan.mathpower;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
        DatabaseReference myRef = database.getReference();

        myRef.orderByChild("_age");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Player p = new Player();
                ArrayList<Player> ppl = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    ppl.add(userSnapshot.getValue(Player.class));
                    arrayAdapter.addAll(userSnapshot.getValue(Player.class));

                }

                for (int i = 0; i < ppl.size(); i++) {
                    //arrayAdapter.addAll(ppl);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }
}
