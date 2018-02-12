package com.michaelkatan.PowerMath;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by MichaelKatan on 17/01/2018.
 */



public class GameManager extends Activity {
    public static int NEWLEVEL = 1;
    public int totalscore = 0;
    public int rightAnswarsStreak = 0;
    public int totalQuastions = 0;
    public long timeLeft;

    public boolean streak = false;
    public boolean backPressed = false;
    public boolean timeEnd = false;

    MediaPlayer ring;
    Player player;
    ArrayList<Class> levels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ring= MediaPlayer.create(GameManager.this,R.raw.ameno);
        ring.setVolume(0.5f,0.5f);
        ring.start();


        int mode;
        mode = getIntent().getExtras().getInt("practice", 0);


        player = new Player();
        levels = new ArrayList<>();

        levels.add(AmericanQuiz.class);
        levels.add(TrueFalseSign.class);

        player.set_lives(3);
        if (mode == 0) {
            timeLeft = 30000;
        } else {
            timeLeft = 9999999;
        }
        startRandomLevel();

    }



    private void startRandomLevel() {
        int temp;
        temp = (int) ((Math.random() * 100) % levels.size());

        Intent intent = new Intent(GameManager.this, levels.get(temp));
        intent.putExtra("score", totalscore);
        intent.putExtra("answerStreak", rightAnswarsStreak);
        intent.putExtra("total", totalQuastions);
        intent.putExtra("lives", player.get_lives());
        intent.putExtra("time", timeLeft);
        intent.putExtra("onStreak", streak);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


        startActivityForResult(intent, NEWLEVEL);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        long temp;
        if (requestCode == NEWLEVEL) {
            if (resultCode == RESULT_OK) {
                streak = true;

                temp = data.getLongExtra("time", 30000);
                temp = temp / 1000;
                temp = temp * 1000;

                timeLeft = temp;
                totalQuastions++;
                rightAnswarsStreak++;
                totalscore = totalscore + (int) temp / 1000;

                startRandomLevel();

            } else {
                streak = false;
                temp = data.getLongExtra("time", 30000);
                backPressed = data.getExtras().getBoolean("back", false);
                rightAnswarsStreak = 0;
                timeEnd = data.getExtras().getBoolean("endTime", false);

                temp = temp / 1000;
                temp = temp * 1000;
                timeLeft = temp;

                if (backPressed) {
                    player.set_lives(1);
                    totalscore = 0;
                }

                if (timeEnd) {
                    player.set_lives(1);
                }


                player.set_lives(player.get_lives() - 1);
                if (player.get_lives() == 0) {
                    Toast.makeText(this, "You Lost", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();

                    intent.putExtra("score", totalscore);
                    setResult(RESULT_OK, intent);
                    ring.stop();
                    finish();
                } else {

                    totalQuastions++;
                    startRandomLevel();
                }
            }
        }

    }

}
