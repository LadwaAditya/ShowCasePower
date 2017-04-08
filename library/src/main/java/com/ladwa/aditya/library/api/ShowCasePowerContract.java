package com.ladwa.aditya.library.api;

import com.ladwa.aditya.library.ShowCasePower;

/**
 * Created by Aditya on 08-Apr-17.
 */

public interface ShowCasePowerContract {
    /**
     * Called by the {@link com.ladwa.aditya.library.ShowCasePower.Builder} to show view
     * @param builder
     * @return
     */
    boolean show(ShowCasePower.Builder builder);
}
