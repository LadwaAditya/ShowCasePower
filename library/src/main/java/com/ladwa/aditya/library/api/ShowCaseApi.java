package com.ladwa.aditya.library.api;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;

import com.ladwa.aditya.library.ShowCasePower;
import com.ladwa.aditya.library.target.Target;

/**
 * Defines the Business logic of the CustomView
 * Created by Aditya on 08-Apr-17.
 */
public class ShowCaseApi {

    /**
     * An Interface that defines the Builder contract
     */
    public interface BuilderContract {

        /**
         * Displays the view
         *
         * @return
         */
        ShowCasePower show();

        /**
         * Returns the activity from where {@link ShowCasePower} is called
         *
         * @return
         */
        Activity getActivity();

        /**
         * Set the title of the view
         *
         * @param title
         * @return
         */
        ShowCasePower.Builder setTitle(CharSequence title);

        /**
         * Sets the Content of the View
         *
         * @param content
         * @return
         */
        ShowCasePower.Builder setContent(CharSequence content);


        /**
         * Set the target to be highlighted
         *
         * @param target
         * @return
         */
        ShowCasePower.Builder setTarget(View target);

    }

    /**
     * An interface that defines the Custom View Contract
     */
    public interface ShowCasePowerContract {
        /**
         * Called by the {@link com.ladwa.aditya.library.ShowCasePower.Builder} to show view
         *
         * @param builder
         * @return
         */
        boolean show(ShowCasePower.Builder builder);

        /**
         * Set title for the View to be displayed
         *
         * @param title
         */
        void setTitle(CharSequence title);

        /**
         * Set Content Description for the View
         *
         * @param content
         */
        void setContent(CharSequence content);

        /**
         * Apply the layout parameters for the view
         */
        void applyLayoutParams();

        /**
         * Set the target view
         * @param target
         */
        void setTarget(Target target);

        /**
         * Set the positions to global variables
         * @param positions
         */
        void setPositions(Point positions);

    }
}
