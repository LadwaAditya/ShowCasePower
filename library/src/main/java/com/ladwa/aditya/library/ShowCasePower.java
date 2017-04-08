package com.ladwa.aditya.library;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ladwa.aditya.library.api.ShowCaseApi;
import com.ladwa.aditya.library.target.Target;
import com.ladwa.aditya.library.target.ViewTarget;

/**
 * A Custom {@link android.widget.FrameLayout} that represents Showcasepower
 * Created by Aditya on 08-Apr-17.
 */
public class ShowCasePower extends FrameLayout implements ShowCaseApi.ShowCasePowerContract {

    private static final String TAG = ShowCasePower.class.getSimpleName();

    //Views
    private TextView mTxtTitle, mTxtContents;
    private Button mBtnDismiss;
    private View mContentBox;

    //Custom view
    private Target mTarget;
    private int mXpos;
    private int mYpos;
    private int mGravity;
    private int mContentBottomMargin;
    private int mContentTopMargin;
    private int mShapePadding = 15;

    //View listener
    private UpdateLayoutListener mUpdateLayoutListener;


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
        setWillNotDraw(false);

        //Set up layout listener
        mUpdateLayoutListener = new UpdateLayoutListener();
        getViewTreeObserver().addOnGlobalLayoutListener(mUpdateLayoutListener);


        View content = LayoutInflater.from(context).inflate(R.layout.content_details, this, true);

        //Find Views
        mContentBox = content.findViewById(R.id.content_box);
        mTxtTitle = (TextView) content.findViewById(R.id.txt_title);
        mTxtContents = (TextView) content.findViewById(R.id.txt_content);
        mBtnDismiss = (Button) content.findViewById(R.id.btn_dismiss);



        Log.d(TAG, "Initialized");
    }

    @Override
    public boolean show(Builder builder) {
        ((ViewGroup) builder.getActivity().getWindow().getDecorView()).addView(this);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw");
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mTxtTitle != null && title.length() > 0) {
            mTxtTitle.setText(title);
        }
    }

    @Override
    public void setContent(CharSequence content) {
        if (mTxtTitle != null && content.length() > 0) {
            mTxtContents.setText(content);
        }
    }

    @Override
    public void applyLayoutParams() {
        if (mContentBox != null && mContentBox.getLayoutParams() != null) {
            FrameLayout.LayoutParams layoutParams = (LayoutParams) mContentBox.getLayoutParams();

            boolean layPramFlag = false;

            if (layoutParams.bottomMargin != mContentBottomMargin) {
                layoutParams.bottomMargin = mContentBottomMargin;
                layPramFlag = true;
            }

            if (layoutParams.topMargin != mContentTopMargin) {
                layoutParams.topMargin = mContentTopMargin;
                layPramFlag = true;
            }

            if (layoutParams.gravity != mGravity) {
                layoutParams.gravity = mGravity;
                layPramFlag = true;
            }

            if (layPramFlag)
                mContentBox.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void setTarget(Target target) {
        mTarget = target;
        if (mTarget != null) {
            Point targetPoint = target.getPoint();
            Rect targetRect = target.getBounds();
            setPositions(targetPoint);

            Log.d(TAG, String.valueOf(targetPoint.x));
            Log.d(TAG, String.valueOf(targetPoint.y));

            int radius = Math.max(targetRect.height(), targetRect.width()) / 2;

            // now figure out whether to put content above or below it
            int height = getMeasuredHeight();
            int midPoint = height / 2;
            int yPos = targetPoint.y;

            if (yPos > midPoint) {
                //Top
                mContentTopMargin = 0;
                mContentBottomMargin = (height - yPos) + radius + mShapePadding;
                mGravity = Gravity.BOTTOM;
            } else {
                // Below
                mContentTopMargin = yPos + radius + mShapePadding;
                mContentBottomMargin = 0;
                mGravity = Gravity.TOP;
            }
        }

        applyLayoutParams();
    }

    @Override
    public void setPositions(Point positions) {
        mXpos = positions.x;
        mYpos = positions.y;
    }

    /**
     * A class that watches changes in ViewTree
     */
    private class UpdateLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            setTarget(mTarget);
        }
    }

    /**
     * Builder class that implements {@link com.ladwa.aditya.library.api.ShowCaseApi.BuilderContract}
     * for building {@link ShowCasePower}
     */
    public static class Builder implements ShowCaseApi.BuilderContract {

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
            build().show(this);
            return showCasePower;
        }

        @Override
        public Activity getActivity() {
            return activity;
        }

        @Override
        public Builder setTitle(CharSequence title) {
            showCasePower.setTitle(title);
            return this;
        }

        @Override
        public Builder setContent(CharSequence content) {
            showCasePower.setContent(content);
            return this;
        }

        @Override
        public Builder setTarget(View target) {
            showCasePower.setTarget(new ViewTarget(target));
            return this;
        }
    }
}
