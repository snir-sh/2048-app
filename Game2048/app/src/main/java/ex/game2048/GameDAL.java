package ex.game2048;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GameDAL {

    private GameDB helper;

    public GameDAL(Context context){
        helper = new GameDB(context);
    }

    public void insert(int size, int target, int score)
    {
        //get DB
        SQLiteDatabase db = helper.getWritableDatabase();

        //values to save
        ContentValues values = new ContentValues();

        values.put("size", size);
        values.put("target", target);
        values.put("score", score);

        //save the values
        db.insert("SCORES", null, values);
        db.close();
    }

    public Integer getScores(int size, int target) {

        //get DB
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM SCORES WHERE size=" + size + " and target=" + target +";", null);

        if (c!=null) {
            while (c.moveToNext()) {
                int index = c.getColumnIndex("score");
                return c.getInt(index);
            }
        }
        return 0;
    }
}
