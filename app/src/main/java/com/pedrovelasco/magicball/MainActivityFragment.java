package com.pedrovelasco.magicball;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


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
        bg = (ImageView) rootView.findViewById(R.id.imageView);

                text.setText(MakePrediction.getPrediction(getContext()));
                animatedball();


    }






    public void animatedball(){
        bg.setBackgroundResource(R.drawable.ball_animation);


        AnimationDrawable frameAnimation = (AnimationDrawable) bg.getBackground();
        if(frameAnimation.isRunning()){
            frameAnimation.stop();
        }
        frameAnimation.start();
    }




}


