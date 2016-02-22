package ex.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class game_activity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private static GameView gameView;
    private SharedPreferences preferences; //SharedPreferences for the settings and more
    private TextView best_scoreTXT, current_scoreTXT;  // The texts in the activity
    private ImageView restart;


    private int Bscore, Cscore; // The game scores
    private ImageView MuteCMD;


    // Initialize all the activity elements
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!MusicManager.BGMusic)
            MusicManager.pause();

        setContentView(R.layout.activity_game_activity);
        gameView = (GameView)findViewById(R.id.gameView);
        best_scoreTXT = (TextView)findViewById(R.id.best_scoreTXT);
        current_scoreTXT = (TextView)findViewById(R.id.current_scoreTXT);
        restart = (ImageView)findViewById(R.id.restart_icon);
        MuteCMD = (ImageView)findViewById(R.id.MuteCMD);
        restart.setOnClickListener(this);
        gameView.setOnTouchListener(this);
        MuteCMD.setOnClickListener(this);

        if (MusicManager.BGMusic)
            MuteCMD.setBackgroundResource(R.drawable.unmute2);
        else
            MuteCMD.setBackgroundResource(R.drawable.mute2);
        getScoreFromPreferences();

    }


    // Get all data from preferences and show in the text
    private void getScoreFromPreferences()
    {
        preferences =  getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);
        Bscore = preferences.getInt("best_score", Settings.DEFAULT_SCORE);
        String current = "Current\n" + 0;
        String best = "Best\n" + Bscore;
        current_scoreTXT.setText(current);
        best_scoreTXT.setText(best);

        gameView.getScore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MusicManager.BGMusic)
            MusicManager.start(this,R.raw.thrones);
        getScoreFromPreferences();
        if (MusicManager.BGMusic)
            MuteCMD.setBackgroundResource(R.drawable.unmute2);
        else
            MuteCMD.setBackgroundResource(R.drawable.mute2);
    }


    // Sends all touch events in the view to the game view class and update according the answer
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(gameView.onTouchEvent(event))
        {
            Cscore = gameView.getScore();
            String current = "Current\n" + Cscore;
            current_scoreTXT.setText(current);

            if(Cscore > Bscore)
            {
                Bscore = Cscore;
                String best = "Best\n" + Bscore;
                best_scoreTXT.setText(best);

            }
        }
        gameView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!MusicManager.BGMusic)
            MusicManager.pause();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("best_score", Bscore);
        editor.apply();

    }

    @Override
    protected void onStop() {
        MusicManager.pause();
        super.onStop();
    }

    // Listener for touch events if we need to restart the game or to mute the music
    @Override
    public void onClick(View v) {
        if(v.getId() == restart.getId())
            restartGame();
        else if(v.getId() == MuteCMD.getId())
            muteUnMute();
    }

//    Function that checks if we need to mute or not and do it
    private void muteUnMute() {
        if (Integer.parseInt(MuteCMD.getTag().toString()) == 1)
        {
            MusicManager.BGMusic = false;
            MusicManager.pause();
            MuteCMD.setBackgroundResource(R.drawable.mute2);
            MuteCMD.setTag(2);
        }
        else {
            MusicManager.BGMusic = true;
            MusicManager.start(this,R.raw.thrones);
            MuteCMD.setBackgroundResource(R.drawable.unmute2);
            MuteCMD.setTag(1);
        }
    }

    //  Restart the game
    private void restartGame() {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Are you sure you want to restart the game?");
        dlgAlert.setTitle("Restart");
        dlgAlert.setNegativeButton("No", null);
        dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String current = "Current\n" + Cscore;
                current_scoreTXT.setText(current);
                gameView.reStartGame();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();


    }

}