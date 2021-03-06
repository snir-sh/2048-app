package ex.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private ImageView MuteCMD, ConfCMD, InfoCMD, DelCMD;
    private boolean LayoutHidden = true;
    private Spinner squaresSpin; // spinner to choose the board size
    private SharedPreferences preferences; //SharedPreferences for the settings and more
    private GameDAL DAL;
    private TextView scoreTxt;
    private LinearLayout BottomLayout;
    private int Bscore, size;
    private boolean resetScore = false;
    private boolean moveStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayNow = (ImageView)findViewById(R.id.playnow);
        MuteCMD = (ImageView)findViewById(R.id.MuteCMD);
        InfoCMD = (ImageView)findViewById(R.id.InfoCMD);
        ConfCMD = (ImageView)findViewById(R.id.ConfCMD);
        DelCMD = (ImageView)findViewById(R.id.DeleteCMD);

        squaresSpin = (Spinner)findViewById(R.id.squersSpinner);
        scoreTxt = (TextView)findViewById(R.id.scoreTxt);
        BottomLayout = (LinearLayout)findViewById(R.id.BottomLayout);
        preferences = getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);
        DAL = new GameDAL(this);
        // Set the spinner
        String[] squareArray = getResources().getStringArray(R.array.squersSpinner_arr);
        ArrayAdapter<String> squareAdapter  = new CustomArrayAdapter(this,R.layout.spinner_row,squareArray);
        squaresSpin.setAdapter(squareAdapter);

        squaresSpin.setSelection(getBoardSize(size));
        // Set the listeners to all the buttons
        PlayNow.setOnClickListener(this);
        MuteCMD.setOnClickListener(this);
        InfoCMD.setOnClickListener(this);
        ConfCMD.setOnClickListener(this);
        DelCMD.setOnClickListener(this);
        squaresSpin.setOnItemSelectedListener(this);

        // Check if the music button is mute or not
        if (MusicManager.BGMusic) {
            playMusic();
            MuteCMD.setBackgroundResource(R.drawable.unmute2);
        }
        else
            MuteCMD.setBackgroundResource(R.drawable.mute2);
        BottomLayout.setVisibility(LinearLayout.INVISIBLE);

        getPrefes();
        getScoreFromDB();
        showScore();
    }

    // Check witch button has been clicked
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
        else if (v.getId() == DelCMD.getId())
            deleteScore();
    }

    private void playMusic() {
        MusicManager.BGMusic = true;
        MusicManager.start(this, R.raw.thrones);
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
            playMusic();
            MuteCMD.setBackgroundResource(R.drawable.unmute2);
            MuteCMD.setTag(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPrefes();
        setScoreInDB();
        showScore();
        squaresSpin.setSelection(getBoardSize(size));
        if (MusicManager.BGMusic) {
            MuteCMD.setBackgroundResource(R.drawable.unmute2);
            MusicManager.start(this, R.raw.thrones);
        }
        else
            MuteCMD.setBackgroundResource(R.drawable.mute2);
        BottomLayout.setVisibility(LinearLayout.INVISIBLE);
    }

    // Get data from the shared preferences
    private void getPrefes() {
        Bscore = preferences.getInt("best_score",0);
        size = preferences.getInt("board_size", Settings.DEFAULT_BOARD_SIZE);
    }

    // Set data in the prefernces
    private void setPrefes() {

        size = getBoardSize(squaresSpin.getSelectedItemPosition());
        getScoreFromDB();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("board_size", size);
        editor.putInt("best_score", Bscore);
        editor.apply();
    }

    // Get the score from the db according to the board size
    private void getScoreFromDB() {
        Bscore = DAL.getBscore(size);
        if (Bscore == -1)
            Bscore = 0;

    }

    // Set the best score in the database
    private void setScoreInDB() {
        int bScore = preferences.getInt("best_score",0);
        int board_size = preferences.getInt("board_size", Settings.DEFAULT_BOARD_SIZE);
        if(resetScore)
        {
            bScore = Bscore = 0;
            resetScore = false;
        }
        DAL.insert(board_size, bScore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setScoreInDB();
        if (moveStatus && MusicManager.BGMusic)
        {
            moveStatus = false;
            return;
        }
        MusicManager.pause();
        MusicManager.BGMusic = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (moveStatus && MusicManager.BGMusic)
        {
            moveStatus = false;
            return;
        }
        MusicManager.BGMusic = false;
        MusicManager.pause();
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
        moveStatus = true;
        setPrefes();
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
        dlgAlert.setMessage("This application based on the famous game 2048.\n" +
                "In this game the player can choose the number of the squares on the board,\n" +
                "and enjoy listening to Game of Thrones theme song.");
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

    private void deleteScore() {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Are you sure you want to delete best score?");
        dlgAlert.setTitle("Delete best score");
        dlgAlert.setNegativeButton("No", null);
        dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resetScore = true;
                setScoreInDB();
                showScore();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();


    }

}
