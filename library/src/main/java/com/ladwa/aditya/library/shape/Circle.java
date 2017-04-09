package com.ladwa.aditya.library.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ladwa.aditya.library.target.Target;

/**
 * A Circle shape
 * Created by Aditya on 09-Apr-17.
 */
public class Circle implements Shape {
    private int radius = 200;
    private boolean adjustToTarget = true;


    public Circle() {
    }

    public Circle(int radius) {
        this.radius = radius;
    }

    public Circle(Rect bounds) {
        this(getPreferredRadius(bounds));
    }

    public Circle(Target target) {
        this(target.getBounds());
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y, int padding) {
        if (radius > 0) {
            canvas.drawCircle(x, y, radius + padding, paint);
        }
    }

    @Override
    public int getWidth() {
        return radius * 2;
    }

    @Override
    public int getHeight() {
        return radius * 2;
    }

    @Override
    public void updateTarget(Target target) {
        if (adjustToTarget) {
            radius = getPreferredRadius(target.getBounds());
        }
    }

    private static int getPreferredRadius(Rect bounds) {
        return Math.max(bounds.width(), bounds.height()) / 2;
    }
}
