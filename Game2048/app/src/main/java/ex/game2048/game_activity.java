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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);
        gameView = (GameView)findViewById(R.id.gameView);
        best_scoreTXT = (TextView)findViewById(R.id.best_scoreTXT);
        current_scoreTXT = (TextView)findViewById(R.id.current_scoreTXT);
        restart = (ImageView)findViewById(R.id.restart_icon);

        restart.setOnClickListener(this);
        gameView.setOnTouchListener(this);

        getScoreFromPreferences();

    }



    private void getScoreFromPreferences()
    {
        preferences =  getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);
        Bscore = preferences.getInt("best_score", DEFAULT_SCORE);
        Cscore = preferences.getInt("current_score", DEFAULT_SCORE);

        String current = "Current\n" + Cscore;
        String best = "Best\n" + Bscore;
        current_scoreTXT.setText(current);
        best_scoreTXT.setText(best);

        gameView.getScore();

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

    public TextView getBest_scoreTXT()
    {
        return best_scoreTXT;
    }
    public TextView getCurrent_scoreTXT()
    {
        return current_scoreTXT;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == restart.getId())
        {
            String current = "Current\n" + Cscore;
            current_scoreTXT.setText(current);
            gameView.reStartGame();
        }
    }

}