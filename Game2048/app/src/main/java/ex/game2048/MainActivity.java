package ex.game2048;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.concurrent.SynchronousQueue;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton PlayNow;
    private ImageButton MuteCMD;
    private MediaPlayer mp;
    private boolean MuteStatus = false;
    private Spinner squresSpin, targetSpin;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayNow = (ImageButton)findViewById(R.id.playnow);
        MuteCMD = (ImageButton)findViewById(R.id.MuteCMD);
        targetSpin = (Spinner)findViewById(R.id.targetSpinner);
        squresSpin = (Spinner)findViewById(R.id.squersSpinner);

        preferences = getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);


        String[] squareArray = getResources().getStringArray(R.array.squersSpinner_arr);
        String[] targetArray = getResources().getStringArray(R.array.targetSpinner_arr);

        ArrayAdapter<String> squareAdapter  = new CustomArrayAdapter(this,R.layout.spinner_row,squareArray);
        ArrayAdapter<String> targetAdapter  = new CustomArrayAdapter(this,R.layout.spinner_row,targetArray);


        squresSpin.setAdapter(squareAdapter);
        targetSpin.setAdapter(targetAdapter);
        PlayNow.setOnClickListener(this);
        MuteCMD.setOnClickListener(this);

        playMusic();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == PlayNow.getId())
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("board_size",5);
            editor.apply();
            Intent intent = new Intent(this, game_activity.class);
            startActivity(intent);
        }
        else if(v.getId() == MuteCMD.getId())
        {
            if (MuteStatus) {
                mp.start();
                MuteStatus = false;

            }
            else {
                if (mp != null)
                {
                    mp.pause();
                    MuteStatus = true;
                }
            }
        }
    }

    private void playMusic() {
        mp = MediaPlayer.create(this, R.raw.thrones);
        mp.start();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
