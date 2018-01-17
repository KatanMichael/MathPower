package com.michaelkatan.PowerMath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;


/**
 * Created by MichaelKatan on 17/01/2018.
 */

public class GameManager extends Activity {
    public static int NEWLEVEL = 1;
    public int totalscore = 0;

    Player player;
    ArrayList<Class> levels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = new Player();
        levels = new ArrayList<>();

        levels.add(AmericanQuiz.class);


        player.set_lives(3);

        startRandomLevel();


    }


    private void startRandomLevel() {
        Intent intent = new Intent(GameManager.this, levels.get(0));
        startActivityForResult(intent, NEWLEVEL);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEWLEVEL) {
            if (resultCode == RESULT_OK) {
                int backScore;
                backScore = data.getExtras().getInt("score");
                totalscore = totalscore + backScore;
                player.set_lives(player.get_lives() - 1);


            }
        }

    }
}
