package com.ciaston.przemek.psr;

import android.view.View;
import android.support.design.widget.Snackbar;

/**
 * Created by Przemek on 2017-07-21.
 */

public class MyMethod {

    public static void ShowSnackbar(View view, String text){
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
