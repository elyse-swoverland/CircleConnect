package com.elyseswoverland.circleconnect.ui.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputUtil {
    public static void hideKeyboard(View focusedView) {
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) focusedView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }
}
