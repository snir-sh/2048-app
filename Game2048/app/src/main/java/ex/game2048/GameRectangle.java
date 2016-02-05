package ex.game2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Ilya on 2/5/2016.
 */
public class GameRectangle {
    private Paint _paint;
    private Paint textColor = new Paint();
    private Context context;
    private int _x, _width, _y, _num;
    public GameRectangle(int x, int y, int width, Paint paint, int num, Context context)
    {
        _x = x;
        _y = y;
        _width = width;
        _paint = paint;
        if (num < 8)
            textColor.setColor(Color.BLACK);
//        else
//            textColor.setColor(context.getColor(R.color.textColor));

    }


    public void draw(Canvas canvas)
    {
        canvas.drawRect(_x, _y, _x + _width,_y +_width,_paint);
        canvas.drawText(_num +"",_x + _width/2 - 5, _y + _width/2 + 5,textColor);
    }
}
