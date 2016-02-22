package ex.game2048;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.GestureOverlayView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class game_activity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private static GameView gameView;
    private SharedPreferences preferences; //SharedPreferences for the settings and more
    private TextView best_scoreTXT, current_scoreTXT;
    private ImageView restart;
    private final int DEFAULT_SCORE = 0;

    private int Bscore, Cscore; // The game scores
    private ImageView MuteCMD;

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


    private void getScoreFromPreferences()
    {
        preferences =  getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);
        Bscore = preferences.getInt("best_score", DEFAULT_SCORE);
        Log.d("score", "Bscore in game: " + Bscore);

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
    @Override
    public void onClick(View v) {
        if(v.getId() == restart.getId())
        {
            String current = "Current\n" + Cscore;
            current_scoreTXT.setText(current);
            gameView.reStartGame();
        }
        else if(v.getId() == MuteCMD.getId())
            muteUnMute();
    }

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

}