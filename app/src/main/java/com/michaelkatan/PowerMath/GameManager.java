package com.michaelkatan.PowerMath;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
    public long timeLeft = 30;
    SharedPreferences sharedPreferences;
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


        sharedPreferences = getSharedPreferences("timeLeft", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("time", timeLeft);
        editor.commit();

        updateTime();
        startRandomLevel();

    }

    private void updateTime() {

    }


    private void startRandomLevel() {
        int temp;
        temp = (int) ((Math.random() * 100) % levels.size());
        updateTime();
        Intent intent = new Intent(GameManager.this, levels.get(temp));
        intent.putExtra("score", totalscore);
        intent.putExtra("total", totalQuastions);
        intent.putExtra("lives", player.get_lives());
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


        startActivityForResult(intent, NEWLEVEL);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEWLEVEL) {
            if (resultCode == RESULT_OK) {
                // int temp;
                updateTime();
                //timeLeft = temp;
                totalQuastions++;
                totalscore++;
                startRandomLevel();

            } else {
                long temp;
                temp = sharedPreferences.getLong("time", 30000);

                if (temp == 0) {
                    player.set_lives(1);
                }

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
