package com.ladwa.aditya.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * A Custom {@link android.widget.FrameLayout} that represents Showcasepower
 * Created by Aditya on 08-Apr-17.
 */
public class ShowCasePower extends FrameLayout {

    private static final String TAG = ShowCasePower.class.getSimpleName();

    public ShowCasePower(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ShowCasePower(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowCasePower(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShowCasePower(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    /**
     * Basic initialization which is called when the view is created
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.content_details, this, true);
        Log.d(TAG, "Called");
    }
}
