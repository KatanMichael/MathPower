package com.michaelkatan.PowerMath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;


/**
 * Created by MichaelKatan on 17/01/2018.
 */

public class GameManager extends Activity {

    Player player;
    ArrayList<Class> levels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = new Player();
        levels = new ArrayList<>();

        levels.add(AmericanQuiz.class);


        player.set_lives(3);


    }


    private void startRandomLevel() {
        Intent intent = new Intent(GameManager.this, levels.get(0));
        startActivity(intent);
        ;
    }
}
