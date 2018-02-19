package com.ciaston.przemek.psr;

import android.content.Context;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Przemek on 2017-07-21.
 */

public class MyMethod {

    public static void showSnackbar(View view, String text){
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void randomBackground(RelativeLayout relativeLayout){
        int[] bArray = new int[]{
                R.drawable.bgame1,
                R.drawable.bgame2,
                R.drawable.bgame3,
                R.drawable.bgame4,
                R.drawable.bgame5,
                R.drawable.bgame6,
                R.drawable.bgame7
        };

        int arrLen = bArray.length;
        Random random = new Random();
        int r = random.nextInt(arrLen);
        relativeLayout.setBackgroundResource(bArray[r]);
    }
}
