package com.michaelkatan.PowerMath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by MichaelKatan on 17/01/2018.
 */

public class GameManager extends Activity {
    public static int NEWLEVEL = 1;
    public int totalscore = 0;
    public int totalQuastions = 0;

    Player player;
    ArrayList<Class> levels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = new Player();
        levels = new ArrayList<>();

        levels.add(AmericanQuiz.class);
        levels.add(TrueFalseSign.class);

        player.set_lives(3);

        startRandomLevel();

    }


    private void startRandomLevel() {
        int temp;
        temp = (int) ((Math.random() * 100) % levels.size());

        Intent intent = new Intent(GameManager.this, levels.get(temp));
        intent.putExtra("score", totalscore);
        intent.putExtra("total", totalQuastions);
        intent.putExtra("lives", player.get_lives());

        startActivityForResult(intent, NEWLEVEL);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEWLEVEL) {
            if (resultCode == RESULT_OK) {
                totalQuastions++;
                totalscore++;
                startRandomLevel();

            } else {
                player.set_lives(player.get_lives() - 1);
                Toast.makeText(this, "" + player.get_lives() + " More Lives Left", Toast.LENGTH_SHORT).show();
                if (player.get_lives() == 0) {
                    Toast.makeText(this, "You Lost", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("score", totalscore);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    totalQuastions++;
                    startRandomLevel();
                }
            }
        }

    }
}
