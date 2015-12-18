package com.pedrovelasco.magicball;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

     static TextView text;
    static ImageView bg;
    View rootView;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        animatedBallStart();



    return rootView;
    }


    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);



        mSensorManager = (SensorManager)  ((MainActivity)getActivity()).getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {

            predictionAction();
            }
        });




    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }





    public void predictionAction() {

        text = (TextView) rootView.findViewById(R.id.textView);

        AlphaAnimation txta = new AlphaAnimation(0.0f, 1.0f);

        txta.setDuration(1500);
        //para que no se repita
        txta.setFillAfter(true);


                text.setText(MakePrediction.getPrediction(getContext()));
               animatedBallShake();
            text.setAnimation(txta);
            text.startAnimation(txta);



    }






    public void animatedBallStart(){

        bg = (ImageView) rootView.findViewById(R.id.imageView);
        bg.setBackgroundResource(R.drawable.ball_animation);


        AnimationDrawable frameAnimation = (AnimationDrawable) bg.getBackground();
        frameAnimation.start();
    }



    public void animatedBallShake(){

        bg = (ImageView) rootView.findViewById(R.id.imageView);
        bg.setBackgroundResource(R.drawable.ball_animation_shake);


        final AnimationDrawable frameAnimation = (AnimationDrawable) bg.getBackground();
        if(frameAnimation.isRunning()){
           frameAnimation.stop();
        }

        frameAnimation.start();
        checkIfAnimationDone(frameAnimation);




    }



    /*
    Recursivo que no termina hasta que la animationdrawable que se le pase termine
     */
    private void checkIfAnimationDone(AnimationDrawable anim){
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                    checkIfAnimationDone(a);
                } else{
                    animatedBallStart();
                }
            }
        }, timeBetweenChecks);
    };


}


