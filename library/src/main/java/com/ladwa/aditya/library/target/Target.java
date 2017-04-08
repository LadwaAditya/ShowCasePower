package com.ladwa.aditya.library.target;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * An interface that defines a target to showcase
 * Created by Aditya on 08-Apr-17.
 */
public interface Target {
    Point getPoint();

    Rect getBounds();
}
