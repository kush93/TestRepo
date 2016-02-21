package com.example.kushal.Handy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * View class for handwriting functionality.
 * notepadCreate() -- Creation of writing area.
 * changeColor() -- For when a different color is selected.
 * setErase() -- For when Erase button has been pressed.
 * newNote() -- For when New Note button has been pressed.
 *
 * TODO: Find a way to draw a single dot (drawing is done with paths, thus needs a change in X and Y pos)
 */

public class HandwritingView extends View {

    private HandwritingBL handwritingBL = new HandwritingBL();
    private int selectedColor = Color.parseColor("#ff000000"); // Default initial color (black)
    private boolean eraseState=false;
    private Paint penPaint;
    private Paint notepadPaint;
    private Path penPath;
    private Canvas notepadCanvas;
    private Bitmap notepadBitmap;

    public HandwritingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        notepadCreate();
    }

    private void notepadCreate() {
        // For creating the writing space
        penPaint = new Paint(); // Writing is done with penPaint and
        penPath = new Path();   // penPath
        notepadPaint = new Paint();

        // Sets attributes of pen
        penPaint.setColor(selectedColor);
        penPaint.setStrokeWidth(15);
        penPaint.setStyle(Paint.Style.STROKE);
        penPaint.setStrokeJoin(Paint.Join.ROUND);
        penPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    // Found in HandwritingBL
    public void changeColor(String newColor){
        invalidate();
        //FOR BLL
        selectedColor = handwritingBL.changeColor(newColor);
        //Following is in place of BLL
        //selectedColor = Color.parseColor(newColor);
        penPaint.setColor(selectedColor);
    }

    // Found in HandwritingBL
    public void setErase(boolean isErase){
        // Set eraseState
        eraseState=isErase;
        //FOR BLL
        penPaint.setColor(handwritingBL.setErase(selectedColor, eraseState));
        //Following is in place of BLL
        /**if(eraseState){
            penPaint.setColor(Color.parseColor("#ffffffff")); // Note that changeColor is not used
            // so as to maintain original
            // selectedColor
        }
        else {
            penPaint.setColor(selectedColor);
        }**/
    }

    public void newNote(){
        notepadCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        notepadBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        notepadCanvas = new Canvas (notepadBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawBitmap(notepadBitmap, 0, 0, notepadPaint);
        canvas.drawPath(penPath, penPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //For user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                penPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                penPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                notepadCanvas.drawPath(penPath, penPaint);
                penPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
}