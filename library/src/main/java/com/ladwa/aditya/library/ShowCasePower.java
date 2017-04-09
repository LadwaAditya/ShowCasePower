package com.ladwa.aditya.library;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ladwa.aditya.library.api.ShowCaseApi;
import com.ladwa.aditya.library.shape.Circle;
import com.ladwa.aditya.library.shape.Shape;
import com.ladwa.aditya.library.target.Target;
import com.ladwa.aditya.library.target.ViewTarget;

/**
 * A Custom {@link android.widget.FrameLayout} that represents Showcasepower
 * Created by Aditya on 08-Apr-17.
 */
public class ShowCasePower extends FrameLayout implements ShowCaseApi.ShowCasePowerContract,View.OnClickListener {

    private static final String TAG = ShowCasePower.class.getSimpleName();
    private static final String DEFAULT_MASK_COLOUR = "#dd335075";
    private static final int DEFAULT_SHAPE_PADDING = 15;

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
    private int mBackgroundColor;
    private Paint mEraser;
    private Shape mShape;

    //View listener
    private UpdateLayoutListener mUpdateLayoutListener;
    private Bitmap mBitmap;
    private Canvas mCanvas;


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

        mBackgroundColor = Color.parseColor(DEFAULT_MASK_COLOUR);

        View content = LayoutInflater.from(context).inflate(R.layout.content_details, this, true);

        //Find Views
        mContentBox = content.findViewById(R.id.content_box);
        mTxtTitle = (TextView) content.findViewById(R.id.txt_title);
        mTxtContents = (TextView) content.findViewById(R.id.txt_content);
        mBtnDismiss = (Button) content.findViewById(R.id.btn_dismiss);

        mBtnDismiss.setOnClickListener(this);
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

        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();

        if (mBitmap == null || mCanvas == null) {
            if (mBitmap != null) mBitmap.recycle();
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mCanvas.drawColor(mBackgroundColor);

        if (mEraser == null) {
            mEraser = new Paint();
            mEraser.setColor(0xFFFFFFFF);
            mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
        }

        mShape.draw(mCanvas, mEraser, mXpos, mYpos, DEFAULT_SHAPE_PADDING);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        createReveal();
        Log.d(TAG, "onDraw");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createReveal() {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(this, mXpos, mYpos, 0, Math.max(this.getWidth(), this.getHeight()));
        circularReveal.setDuration(400);
        circularReveal.start();
        mContentBox.setVisibility(VISIBLE);
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

            if (mShape != null) {
                mShape.updateTarget(mTarget);
                radius = mShape.getHeight() / 2;
            }

            if (yPos > midPoint) {
                //Top
                mContentTopMargin = 0;
                mContentBottomMargin = (height - yPos) + radius + DEFAULT_SHAPE_PADDING;
                mGravity = Gravity.BOTTOM;
            } else {
                // Below
                mContentTopMargin = yPos + radius + DEFAULT_SHAPE_PADDING;
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

    @Override
    public void setShape(Shape shape) {
        mShape = shape;
    }

    @Override
    public void removeFromWindow() {
        //Remove view from Window
        if (getParent() != null && getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).removeView(this);
        }
        //Clear variables
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
        mEraser = null;
        mCanvas = null;

        //Clear listeners
        getViewTreeObserver().removeGlobalOnLayoutListener(mUpdateLayoutListener);
        mUpdateLayoutListener = null;
    }

    @Override
    public void onClick(View v) {
        removeFromWindow();
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
            showCasePower.setShape(new Circle(showCasePower.mTarget));
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
