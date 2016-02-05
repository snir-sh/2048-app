package ex.game2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;

/**
 * Created by Ilya on 2/5/2016.
 */
public class GameView extends View {

    private int right, top, width, height , recwidth;
    private int board_size;
    private static final int BOARD_SIZE = 16;
    private SharedPreferences preferences;
    private Paint myPaint, numberPaint;
    private SlidesGame game;
    private boolean start = false;
//    private LinkedList<GameRectangle> board;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        preferences = context.getSharedPreferences("prefees@!",Context.MODE_PRIVATE);
        board_size = preferences.getInt("board_size", BOARD_SIZE);
        game = new SlidesGame(4);
        myPaint = new Paint();
        myPaint.setDither(true);
        myPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setColor(Color.GREEN);

        numberPaint = new Paint();
        myPaint.setDither(true);
        myPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setColor(Color.GREEN);

    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        right = getPaddingRight();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());
        try {

            start = true;
//            createMatrix();
        }catch (Exception e){}
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int w = getWidth();
//        int h = getHeight();
        if (start) {
            recwidth = width / (game.SIZE+2);
            int[][] a = game.getBoard();
            int x, y;
            x = y = recwidth/2;
            myPaint.setColor(Color.BLUE);
//            canvas.drawRect(x,y-10,(x+4*recwidth + 50),(y+4*recwidth +10),myPaint);
            Log.d("draw", "view width: " + width);
//            Log.d("draw", "")
//            canvas.drawRect(x+1,y+1,(recwidth-2)*2,(recwidth-2)*2,myPaint);
            Log.d("draw", "x: " + (x + 1) + " y: " + (y + 1) + " width: " + (recwidth - 2));
            for (int i = 0; i < a.length; i++, y += recwidth) {
                x = recwidth;
                for (int j = 0; j < a.length; j++, x += recwidth)
                    if (a[i][j] != SlidesBoard.EMPTY) {
                        numberPaint.setColor(Color.GREEN);
                        canvas.drawRect(x + 1, y + 1, x + recwidth - 10, y + recwidth - 10, numberPaint);
                        myPaint.setColor(Color.RED);
                        canvas.drawText(a[i][j] +"",x + recwidth/2 - 5, y + recwidth/2 + 5,myPaint);
                    }
                    else {
                        myPaint.setColor(Color.rgb(196,196,196));
                        canvas.drawRect(x + 1, y + 1, x + recwidth - 10, y + recwidth - 10, myPaint);
                    }
            }
        }
    }
    private void createMatrix()
    {


    }


//        Color color = Color.RED;
//        switch (num) {
//            case 2:
//                color = Color.decode( "#F5F5F5");
//                break;
//            case 4:
//                color = Color.decode( "#FFFAFA");
//                break;
//            case 8:
//                color = Color.getHSBColor( 255, 178, 102);
//                break;
//            case 16:
//                color = Color.decode("#F4A460");
//                break;
//            case 32:
//                color = Color.decode("#FA8072");
//                break;
//            case 64:
//                color = Color.decode("#FF6347");
//                break;
//            case 128:
//                color = Color.decode("#FF7F50");
//                break;
//            case 256:
//                color = Color.decode("#FFD700");
//                break;
//            case 512:
//                color = Color.decode("#DAA520");
//                break;
//            case 1024:
//                color = Color.decode("#B8860B0");
//                break;
//            case 2048:
//                color = Color.decode("#FF0000");
//                break;
//        }
//        return color;
//    }
}
