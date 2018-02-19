package com.ciaston.przemek.psr.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Przemek on 2017-07-21.
 */

public class Utils {

    public static void defaultSnack(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void defaultToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void randomBackgroundColor(RelativeLayout relativeLayout) {
        Random random = new Random();

        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        int color = Color.rgb(r, g, b);
        relativeLayout.setBackgroundColor(color);
    }
}
