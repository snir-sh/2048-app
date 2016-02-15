package ex.game2048;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.concurrent.SynchronousQueue;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView PlayNow;
    private ImageView MuteCMD;
    private MediaPlayer mp;
    private boolean MuteStatus = false;
    private Spinner squaresSpin, targetSpin;
    private SharedPreferences preferences; //SharedPreferences for the settings and more
    private final int DEFAULT_TARGET = 1; //0 mean 1024, 1 mean 2048, 2 mean 4096
    private final int DEFAULT_SQURES = 0; //0 mean 4x4, 1 mean 5x5, 2 mean 6x6
    private GameDAL DAL;
    private TextView scoreTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayNow = (ImageView)findViewById(R.id.playnow);
        MuteCMD = (ImageView)findViewById(R.id.MuteCMD);
        targetSpin = (Spinner)findViewById(R.id.targetSpinner);
        squaresSpin = (Spinner)findViewById(R.id.squersSpinner);
        scoreTxt = (TextView)findViewById(R.id.scoreTxt);

        preferences = getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);
        DAL = new GameDAL(this);

        String[] squareArray = getResources().getStringArray(R.array.squersSpinner_arr);
        String[] targetArray = getResources().getStringArray(R.array.targetSpinner_arr);

        ArrayAdapter<String> squareAdapter  = new CustomArrayAdapter(this,R.layout.spinner_row,squareArray);
        ArrayAdapter<String> targetAdapter  = new CustomArrayAdapter(this,R.layout.spinner_row,targetArray);

        squaresSpin.setAdapter(squareAdapter);
        targetSpin.setAdapter(targetAdapter);

        squaresSpin.setSelection(DEFAULT_SQURES);
        targetSpin.setSelection(DEFAULT_TARGET);
        PlayNow.setOnClickListener(this);
        MuteCMD.setOnClickListener(this);

        playMusic();
        showScore();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == PlayNow.getId())
            playNow();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void playNow() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("board_size", getBoardSize());
        editor.putInt("board_target", getBoardTarget());
        editor.apply();

        Intent intent = new Intent(this, game_activity.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "status " + getBoardSize() + " " + getBoardTarget(), Toast.LENGTH_SHORT).show();
    }

    private int getBoardSize()
    {
        switch (squaresSpin.getSelectedItemPosition()) {
            case 0: return 4;
            case 1: return 5;
            case 2: return 6;
        }
        return 0;
    }

    private int getBoardTarget()
    {
        switch (targetSpin.getSelectedItemPosition()) {
            case 0: return 1024;
            case 1: return 2048;
            case 2: return 4096;
        }
        return 1;
    }

    private void showScore()
    {
        String score = DAL.getScores(squaresSpin.getSelectedItemPosition(), targetSpin.getSelectedItemPosition());
        scoreTxt.setText(getBoardSize() + "x" + getBoardSize() + " " + getBoardTarget() + " " + score);
    }

}
