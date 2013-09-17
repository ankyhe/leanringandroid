package com.gmail.at.gerystudio.drawerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-14
 * Time: AM11:48
 * To change this template use File | Settings | File Templates.
 */
public class DrawView extends View {

    private static final String LOG_TAG = "Drawer";

    private Paint backgroundPaint = new Paint();
    private Paint linePaint = new Paint();

    private List<List<PointF>> pointsArr = new ArrayList<List<PointF>>();
    private List<PointF> currentPoints = null;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        backgroundPaint.setColor(0xfff8efe0);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2.0f);
    }

    public void clear() {
        pointsArr.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);    //To change body of overridden methods use File | Settings | File Templates.
        canvas.drawPaint(backgroundPaint);

        for (List<PointF> points : pointsArr) {
            for (int i = 0 ; i < points.size() - 1; ++i) {
                float x0 = points.get(i).x;
                float y0 = points.get(i).y;
                float x1 = points.get(i+1).x;
                float y1 = points.get(i+1).y;
                canvas.drawLine(x0, y0, x1, y1, linePaint);
            }

        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        PointF currentPoint = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(LOG_TAG, "  ACTION_DOWN");
                currentPoints = new ArrayList<PointF>();
                currentPoints.add(currentPoint);
                pointsArr.add(currentPoints);
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i(LOG_TAG, "  ACTION_MOVE");
                if (currentPoints != null) {
                    currentPoints.add(currentPoint);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                Log.i(LOG_TAG, "  ACTION_UP");
                if (currentPoints != null) {
                    currentPoints.add(currentPoint);
                    currentPoints = null;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.i(LOG_TAG, "  ACTION_CANCEL");
                if (currentPoints != null) {
                    currentPoints.add(currentPoint);
                    currentPoints = null;
                }
                break;
        }

        return true;
    }
}
