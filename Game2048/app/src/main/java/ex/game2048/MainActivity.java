package ex.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView PlayNow;
    private ImageView MuteCMD, ConfCMD, InfoCMD;
    private MediaPlayer mp;
    private boolean MuteStatus = false;
    private boolean LayoutHidden = true;
    private Spinner squaresSpin, targetSpin;
    private SharedPreferences preferences; //SharedPreferences for the settings and more
    private final int DEFAULT_TARGET = 1; //0 mean 1024, 1 mean 2048, 2 mean 4096
    private final int DEFAULT_SQURES = 0; //0 mean 4x4, 1 mean 5x5, 2 mean 6x6
    private GameDAL DAL;
    private TextView scoreTxt;
    private LinearLayout BottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayNow = (ImageView)findViewById(R.id.playnow);
        MuteCMD = (ImageView)findViewById(R.id.MuteCMD);
        InfoCMD = (ImageView)findViewById(R.id.InfoCMD);
        ConfCMD = (ImageView)findViewById(R.id.ConfCMD);
        targetSpin = (Spinner)findViewById(R.id.targetSpinner);
        squaresSpin = (Spinner)findViewById(R.id.squersSpinner);
        scoreTxt = (TextView)findViewById(R.id.scoreTxt);
        BottomLayout = (LinearLayout)findViewById(R.id.BottomLayout);

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
        InfoCMD.setOnClickListener(this);
        ConfCMD.setOnClickListener(this);
        MuteCMD.setBackgroundResource(R.drawable.unmute2);
        BottomLayout.setVisibility(LinearLayout.INVISIBLE);
        playMusic();
        showScore();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == PlayNow.getId())
            playNow();
        else if(v.getId() == ConfCMD.getId())
            confLayout();
        else if(v.getId() == MuteCMD.getId())
        {
                if (Integer.parseInt(MuteCMD.getTag().toString()) == 1)
                {
                    mp.pause();
                    MuteStatus = true;
                    MuteCMD.setBackgroundResource(R.drawable.mute2);
                    MuteCMD.setTag(2);
                }
                else {
                    mp.start();
                    MuteStatus = false;
                    MuteCMD.setBackgroundResource(R.drawable.unmute2);
                    MuteCMD.setTag(1);
                }
        }
        else if(v.getId() == InfoCMD.getId())
        {
            infoDialog();
        }
    }

    private void playMusic() {
        mp = MediaPlayer.create(this, R.raw.thrones);
        mp.start();
    }


    @Override
    protected void onResume() {
        setScoreInDB();
        showScore();
        super.onResume();
    }

    private void setScoreInDB() {
        // get the data from sharedPreferences
        // update Score in dal
        // save the board int[][] boardArray according the board size^2

        // we need to add a current score in db

        // maybe the set in the db need to be in game_activity.. but still we need the function in the dal

    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.pause();
        MuteStatus = true;
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
        editor.putInt("current_score",0);
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
        int score = DAL.getScores(squaresSpin.getSelectedItemPosition(), targetSpin.getSelectedItemPosition());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("best_score", score);
        Log.d("score","best score: " + score);
        scoreTxt.setText(getBoardSize() + "x" + getBoardSize() + " " + getBoardTarget() + " " + score);
    }

    private void infoDialog()
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("infooooooooooooooooooooooooooooooooooooo");
        dlgAlert.setTitle("Information");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private void confLayout()
    {
        if(LayoutHidden) {
            BottomLayout.setVisibility(LinearLayout.VISIBLE);
            LayoutHidden = false;
        }
        else {
            BottomLayout.setVisibility(LinearLayout.INVISIBLE);
            LayoutHidden = true;
        }
    }

}
