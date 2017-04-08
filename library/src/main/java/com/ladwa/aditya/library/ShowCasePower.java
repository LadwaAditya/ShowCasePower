package com.ladwa.aditya.library;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ladwa.aditya.library.api.BuilderContract;
import com.ladwa.aditya.library.api.ShowCasePowerContract;

/**
 * A Custom {@link android.widget.FrameLayout} that represents Showcasepower
 * Created by Aditya on 08-Apr-17.
 */
public class ShowCasePower extends FrameLayout implements ShowCasePowerContract {

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
        Log.d(TAG, "Initialized");
    }

    @Override
    public boolean show(Builder builder) {
        ((ViewGroup) builder.getActivity().getWindow().getDecorView()).addView(this);
        return true;
    }

    /**
     * Builder class that implements {@link BuilderContract}
     * for building {@link ShowCasePower}
     */
    public static class Builder implements BuilderContract {

        private final Activity activity;
        private ShowCasePower showCasePower;

        public Builder(Activity activity) {
            this.activity = activity;
            showCasePower = new ShowCasePower(activity);
        }

        private ShowCasePower build() {
            return showCasePower;
        }

        @Override
        public ShowCasePower show() {
            build();
            return showCasePower;
        }

        @Override
        public Activity getActivity() {
            return activity;
        }
    }
}
