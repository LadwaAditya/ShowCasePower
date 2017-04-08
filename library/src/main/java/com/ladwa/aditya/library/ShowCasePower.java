package com.ladwa.aditya.library;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ladwa.aditya.library.api.ShowCaseApi;

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
        View content = LayoutInflater.from(context).inflate(R.layout.content_details, this, true);

        //Find Views
        mContentBox = content.findViewById(R.id.content_box);
        mTxtTitle = (TextView) content.findViewById(R.id.txt_title);
        mTxtContents = (TextView) content.findViewById(R.id.txt_content);
        mBtnDismiss = (Button) content.findViewById(R.id.btn_dismiss);


        Log.d(TAG, "Initialized");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean show(Builder builder) {
        ((ViewGroup) builder.getActivity().getWindow().getDecorView()).addView(this);
        return true;
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
            build();
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
    }
}
