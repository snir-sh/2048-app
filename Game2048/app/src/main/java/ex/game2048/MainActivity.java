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
    private final int DEFAULT_TARGET = 1; //0 mean 1024, 1 mean 2048, 2 mean 4096 --- only on the spinner
    private final int DEFAULT_SQURES = 0; //0 mean 4x4, 1 mean 5x5, 2 mean 6x6  --- only on the spinner
    private GameDAL DAL;
    private TextView scoreTxt, bestTxt;
    private LinearLayout BottomLayout;
    private int Bscore, target , size;

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
        bestTxt = (TextView)findViewById(R.id.BestTxt);
        preferences = getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);
        DAL = new GameDAL(this);

        String[] squareArray = getResources().getStringArray(R.array.squersSpinner_arr);
        String[] targetArray = getResources().getStringArray(R.array.targetSpinner_arr);

        ArrayAdapter<String> squareAdapter  = new CustomArrayAdapter(this,R.layout.spinner_row,squareArray);
        ArrayAdapter<String> targetAdapter  = new CustomArrayAdapter(this,R.layout.spinner_row,targetArray);

        squaresSpin.setAdapter(squareAdapter);
        targetSpin.setAdapter(targetAdapter);

        getPrefes();
        squaresSpin.setSelection(getBoardSize(size));
        targetSpin.setSelection(getBoardTarget(target));

        PlayNow.setOnClickListener(this);
        MuteCMD.setOnClickListener(this);
        InfoCMD.setOnClickListener(this);
        ConfCMD.setOnClickListener(this);

        squaresSpin.setOnItemSelectedListener(this);
        targetSpin.setOnItemSelectedListener(this);

        MuteCMD.setBackgroundResource(R.drawable.unmute2);
        BottomLayout.setVisibility(LinearLayout.INVISIBLE);
        playMusic();
        getScoreFromDB();
     //   showScore();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == PlayNow.getId())
            playNow();
        else if(v.getId() == ConfCMD.getId())
            confLayout();
        else if(v.getId() == MuteCMD.getId())
            muteUnMute();
        else if(v.getId() == InfoCMD.getId())
            infoDialog();
    }

    private void playMusic() {
        mp = MediaPlayer.create(this, R.raw.thrones);
        mp.start();
    }

    private void muteUnMute() {
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


    @Override
    protected void onResume() {
        Log.d("score","onResume");
        getPrefes();
        setScoreInDB();
     //   getScoreFromDB();
    //    showScore();
        squaresSpin.setSelection(getBoardSize(size));
        targetSpin.setSelection(getBoardTarget(target));
        super.onResume();
    }

    private void getPrefes() {
        Bscore = preferences.getInt("best_score",0);
        size = preferences.getInt("board_size", 4);
        target = preferences.getInt("board_target",2048);
        Log.d("score","getPrefes Bscore: " + Bscore + " size " + size + " target " + target);
    }

    private void getScoreFromDB() {
//        getPrefes();
        Bscore = DAL.getBscore(size, target);
        Log.d("score", "getScoreFromDB " + Bscore);
        if (Bscore == -1)
            Bscore = 0;

    }

    private void setScoreInDB() {
        int bScore = preferences.getInt("best_score",0);
        int board_size = preferences.getInt("board_size", 4);
        int target = preferences.getInt("board_target",2048);

        Log.d("score", "setScoreinDB " + bScore);
        DAL.insert(board_size, target, bScore);
    }

    @Override
    protected void onPause() {
        Log.d("score","onPause");
        setScoreInDB();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mp.pause();
        MuteStatus = true;
        super.onStop();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     //   getScoreFromDB();

        //Configuring size and target after changes
        size = getBoardSize(squaresSpin.getSelectedItemPosition());
        target = getBoardTarget(targetSpin.getSelectedItemPosition());

        setPrefes();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void playNow() {
        Intent intent = new Intent(this, game_activity.class);
        startActivity(intent);
    }

    private int getBoardSize(int s)
    {
        switch (s) {
            case 0: return 4;
            case 1: return 5;
            case 2: return 6;
            case 4: return 0;
            case 5: return 1;
            case 6: return 2;
        }
        return DEFAULT_SQURES;
    }

    private int getBoardTarget(int t)
    {
        switch (t) {
            case 0: return 1024;
            case 1: return 2048;
            case 2: return 4096;
            case 1024: return 0;
            case 2048: return 1;
            case 4096: return 2;
        }
        return DEFAULT_TARGET;
    }

    private void showScore()
    {
        scoreTxt.setText("" + Bscore);
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

    private void setPrefes() {
        //    getScoreFromDB();

        size = getBoardSize(squaresSpin.getSelectedItemPosition());
        target = getBoardTarget(targetSpin.getSelectedItemPosition());
        getScoreFromDB();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("board_size", size);
        editor.putInt("board_target", target);
        editor.putInt("best_score", Bscore);
        editor.apply();
    }
}
