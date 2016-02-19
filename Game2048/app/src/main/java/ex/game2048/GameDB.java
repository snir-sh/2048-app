package ex.game2048;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDB extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppTimeEntries.db";
    private static final String TABLE_NAME = "SCORES";
    private static final String COL_SIZE = "size";
    private static final String COL_TARGET = "target";
    private static final String COL_BSCORE = "score";


    public GameDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME  + " ( " +
                        COL_SIZE + " INTEGER," +
                        COL_TARGET + " INTEGER," +
                        COL_BSCORE + " INTEGER" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
