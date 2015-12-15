package com.pedrovelasco.magicball;

import android.content.Context;

import java.util.Random;

/**
 * Created by pedrovelasco on 15/12/15.
 */
public class MakePrediction {
    public static String getPrediction(Context context){
        String [] p =  context.getResources().getStringArray(R.array.predictions);
        Random randomGenenerator = new Random();
        int randomNumber = randomGenenerator.nextInt(p.length);

        return p[randomNumber];
    }

}
