package ex.game2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Ilya on 2/5/2016.
 */
public class GameView extends View implements GestureDetector.OnGestureListener{

    private int right, top, width, height , recwidth; //Params for the size of the screen
    private int board_size; // Param for the board size
    private GestureDetectorCompat mDetector;

    private SharedPreferences preferences; //SharedPreferences for the settings and more
    private Paint myPaint, numberPaint;
    private SlidesGame game; // The logic object of the game
    private GameRectangle game_board; // The background of the board
    private boolean start = false; // a boolean value to know if the game can start
    private LinkedList<GameRectangle> board; // List of the rectangles of the board



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

    // Initialize the settings
    private void init(Context context)
    {
        preferences = context.getSharedPreferences("prefees@!2048", Context.MODE_PRIVATE);
        board_size = preferences.getInt("board_size", Settings.DEFAULT_BOARD_SIZE);
        game = new SlidesGame(board_size);


        myPaint = new Paint();
        myPaint.setDither(true);
        myPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setColor(Color.GREEN);
        board = new LinkedList<GameRectangle>();

        mDetector = new GestureDetectorCompat(getContext(),this);

    }



    // Initialize the screen params
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        right = getPaddingRight();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());
        try {
            createGameBoard();

            createMatrix();
            start = true;
        }catch (Exception e){}
    }

    // Draw the game on screen
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (start) {
            game_board.draw(canvas);
            for (GameRectangle rec: board ) {
                rec.draw(canvas);
            }

        }
    }

    // Create the board objects of the game into a linked list
    private void createMatrix()
    {

        recwidth = (width - right*2) / (game.SIZE);
        int[][] a = game.getBoard();
        int x, y;
        x = y = right*2;
        for (int i = 0; i < a.length; i++, y += recwidth) {
            x = right*2;
            for (int j = 0; j < a.length; j++, x += recwidth)
                if (a[i][j] != SlidesBoard.EMPTY) {
                    board.add(new GameRectangle(x, y, x + recwidth, y + recwidth, recwidth / 2, myPaint, a[i][j]));
                }
                else {
                    board.add(new GameRectangle(x , y , x + recwidth  ,y + recwidth ,recwidth/2, myPaint, a[i][j]));
                }
        }



    }

    // Create the background board
    private void createGameBoard()
    {
        Paint board_paint = new Paint();
        myPaint.setDither(true);
        myPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        myPaint.setStrokeJoin(Paint.Join.ROUND);

        game_board = new GameRectangle(right,right,width+right, width+right,0,board_paint,-1);

    }





    // Get the angle of the user swipe
    private double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return (rad*180/Math.PI + 180)%360;
    }

    // Move the board according to the angle that been given
    public void moveBoardByAngle(double angle){
        if(inRange(angle, 45, 135)){
            game.moveUp();
        }
        else if(inRange(angle, 0,45) || inRange(angle, 315, 360)){
            game.moveRight();
        }
        else if(inRange(angle, 225, 315)){
            game.moveDown();
        }
        else{
            game.moveLeft();
        }

        createMatrix();
        invalidate();

    }
    // Check if the angle in rang between init and end params
    private boolean inRange(double angle, float init, float end){
        return (angle >= init) && (angle < end);
    }



    // Check if there was a touch event on the view, if there was the function checks witch direction move the board
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        return mDetector.onTouchEvent(event);
    }

    public int getScore()
    {
        return game.getScore();
    }
    public void reStartGame()
    {
        game = new SlidesGame(board_size);
        createMatrix();
        invalidate();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float x1,x2,y1,y2 ;
        x1 = e1.getX();
        y1 = e1.getY();

        x2 = e2.getX();
        y2 = e2.getY();
        double angle =  getAngle(x1,y1,x2,y2);
        moveBoardByAngle(angle);

        return true;
    }

    public LinkedList<GameRectangle> getBoard() {
        return board;
    }
}
