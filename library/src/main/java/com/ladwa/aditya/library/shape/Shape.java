package com.ladwa.aditya.library.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ladwa.aditya.library.target.Target;

/**
 * An Abstract Type that defines properties of a shape
 * Created by Aditya on 09-Apr-17.
 */
public interface Shape {
    /**
     * Draws the shape to the canvas
     * @param canvas A canvas object
     * @param paint Paint to draw on canvas
     * @param x Position x
     * @param y Position y
     * @param padding Padding to add extra width or height
     */
    void draw(Canvas canvas, Paint paint, int x, int y, int padding);

    /**
     * Returns the width of the {@link Shape}
     * @return Width of view
     */
    int getWidth();

    /**
     * Returns the height of the {@link Shape}
     * @return
     */
    int getHeight();

    /**
     * Update the Target view
     * @param target A view
     */
    void updateTarget(Target target);
}
