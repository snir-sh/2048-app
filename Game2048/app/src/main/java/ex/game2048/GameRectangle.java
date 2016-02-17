package ex.game2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Ilya on 2/5/2016.
 */
public class GameRectangle {
    private Paint _paint;
    private Paint numberPaint;
    private Context context;
    private int _x, _width, _y, _num, _y1, _x1;
    private int DEFAULT_REC_COLOR = Color.rgb(255,218,185);
    private int BOARD_REC_COLOR = Color.rgb(196, 196, 196);

    public GameRectangle(int x, int y, int x1,int y1, int width, Paint paint, int num)
    {
        _x = x;
        _y = y;
        _x1 = x1;
        _y1 = y1;
        _width = width;
        _paint = paint;
        _num = num;

        numberPaint = new Paint();
        numberPaint.setDither(true);
        numberPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        numberPaint.setStrokeJoin(Paint.Join.ROUND);
        numberPaint.setTextSize(50);
        numberPaint.setTextAlign(Paint.Align.CENTER);
//        else
//            textColor.setColor(context.getColor(R.color.textColor));

    }


    public void draw(Canvas canvas)
    {


        if(_num == -1) {
            _paint.setColor(BOARD_REC_COLOR);
            canvas.drawRect(_x, _y, _x1, _y1, _paint);
            return;
        }
        int color  = getColor(_num);
        _paint.setColor(color);
        canvas.drawRect(_x+10, _y+10, _x1-5, _y1-5, _paint);

        if(_num != 0)
        {
            numberPaint.setColor(Color.rgb(139,69,19));
            String num = _num +"";
            canvas.drawText(_num + "", _x + _width - num.length(), _y + _width + 15, numberPaint);

        }
    }

    private int getColor(int num)
    {
        int color = DEFAULT_REC_COLOR;
        switch (num) {
            case 2:
                color = Color.parseColor("#F5F5F5");
                break;
            case 4:
                color = Color.parseColor("#FFFAFA");
                break;
            case 8:
                color = Color.rgb(255, 178, 102);
                break;
            case 16:
                color = Color.parseColor("#F4A460");
                break;
            case 32:
                color = Color.parseColor("#FA8072");
                break;
            case 64:
                color = Color.parseColor("#FF6347");
                break;
            case 128:
                color = Color.parseColor("#FF7F50");
                break;
            case 256:
                color = Color.parseColor("#FFD700");
                break;
            case 512:
                color = Color.parseColor("#DAA520");
                break;
            case 1024:
                color = Color.parseColor("#f75461");
                break;
            case 2048:
                color = Color.parseColor("#FF0000");
                break;
            case 4096:
                color = Color.parseColor("#fc9fa6");
                break;
            case 8192:
                color = Color.parseColor("#cdf78a");
                break;

        }

        return color;
    }
}
