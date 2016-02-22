package ex.game2048;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class GameDAL {

    private GameDB helper;

    public GameDAL(Context context){
        helper = new GameDB(context);
    }

    public void insert(int size, int Bscore)
    {
        //get DB
        SQLiteDatabase db = helper.getWritableDatabase();

        //values to save
        ContentValues values = new ContentValues();
        values.put("score", Bscore);
        int num = getBscore(size);
        if(num != -1)
        {
            String where = "size" + "=?" ;
            String[] args = {size +""};
            db.update("SCORES", values, where ,args);
            db.close();
            return;
        }
        values.put("size", size);



        //save the values
        db.insert("SCORES", null, values);
        db.close();
    }

    public Integer getBscore(int size) {

        //get DB
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM SCORES WHERE size=" + size + ";", null);

        if (c!=null) {
            while (c.moveToNext()) {
                int index = c.getColumnIndex("score");
                return c.getInt(index);
            }
        }
        return -1;
    }

}
