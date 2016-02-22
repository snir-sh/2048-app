package ex.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView PlayNow;
    private ImageView MuteCMD, ConfCMD, InfoCMD;
    private boolean MuteStatus = false;
    private boolean LayoutHidden = true;
    private Spinner squaresSpin;
    private SharedPreferences preferences; //SharedPreferences for the settings and more
    private GameDAL DAL;
    private TextView scoreTxt, bestTxt;
    private LinearLayout BottomLayout;
    private int Bscore, size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayNow = (ImageView)findViewById(R.id.playnow);
        MuteCMD = (ImageView)findViewById(R.id.MuteCMD);
        InfoCMD = (ImageView)findViewById(R.id.InfoCMD);
        ConfCMD = (ImageView)findViewById(R.id.ConfCMD);
        squaresSpin = (Spinner)findViewById(R.id.squersSpinner);
        scoreTxt = (TextView)findViewById(R.id.scoreTxt);
        BottomLayout = (LinearLayout)findViewById(R.id.BottomLayout);
        bestTxt = (TextView)findViewById(R.id.BestTxt);
        preferences = getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);
        DAL = new GameDAL(this);

        Settings.initializeSettings(getApplicationContext());


        String[] squareArray = getResources().getStringArray(R.array.squersSpinner_arr);

        ArrayAdapter<String> squareAdapter  = new CustomArrayAdapter(this,R.layout.spinner_row,squareArray);

        squaresSpin.setAdapter(squareAdapter);
        getPrefes();
        squaresSpin.setSelection(getBoardSize(size));

        PlayNow.setOnClickListener(this);
        MuteCMD.setOnClickListener(this);
        InfoCMD.setOnClickListener(this);
        ConfCMD.setOnClickListener(this);

        squaresSpin.setOnItemSelectedListener(this);
        if (MusicManager.BGMusic)
            MuteCMD.setBackgroundResource(R.drawable.unmute2);
        else
            MuteCMD.setBackgroundResource(R.drawable.mute2);
        BottomLayout.setVisibility(LinearLayout.INVISIBLE);
        playMusic();
        getScoreFromDB();
        showScore();
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
        MusicManager.BGMusic = true;
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


    @Override
    protected void onResume() {
        super.onResume();
        if (MusicManager.BGMusic)
            MusicManager.start(this,R.raw.thrones);
        getPrefes();
        setScoreInDB();
        showScore();
        squaresSpin.setSelection(getBoardSize(size));
        if (MusicManager.BGMusic)
            MuteCMD.setBackgroundResource(R.drawable.unmute2);
        else
            MuteCMD.setBackgroundResource(R.drawable.mute2);
        BottomLayout.setVisibility(LinearLayout.INVISIBLE);
    }

    private void getPrefes() {
        Bscore = preferences.getInt("best_score",0);
        size = preferences.getInt("board_size", Settings.DEFAULT_BOARD_SIZE);
    }

    private void setPrefes() {

        size = getBoardSize(squaresSpin.getSelectedItemPosition());
        getScoreFromDB();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("board_size", size);
        editor.putInt("best_score", Bscore);
        editor.apply();
    }

    private void getScoreFromDB() {
        Bscore = DAL.getBscore(size);
        if (Bscore == -1)
            Bscore = 0;

    }

    private void setScoreInDB() {
        int bScore = preferences.getInt("best_score",0);
        int board_size = preferences.getInt("board_size", Settings.DEFAULT_BOARD_SIZE);
        DAL.insert(board_size, bScore);
    }

    @Override
    protected void onPause() {
        setScoreInDB();
        if(!MusicManager.BGMusic)
            MusicManager.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        MusicManager.pause();
        super.onStop();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        size = getBoardSize(squaresSpin.getSelectedItemPosition());
        setPrefes();
        getScoreFromDB();
        showScore();
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
        return Settings.DEFAULT_SQURES;
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

}
