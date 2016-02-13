package ex.game2048;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity1 extends AppCompatActivity {

    private static GameView gameView;
    private SharedPreferences preferences; //SharedPreferences for the settings and more
    private TextView best_scoreTXT, current_scoreTXT;
    private ImageView restart;
    private final int DEFAULT_SCORE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);


        best_scoreTXT = (TextView)findViewById(R.id.best_scoreTXT);
        current_scoreTXT = (TextView)findViewById(R.id.current_scoreTXT);
//        restart = (ImageView)findViewById(R.id.restart_icon);

        best_scoreTXT.setText("sadfaf");
    }
}
