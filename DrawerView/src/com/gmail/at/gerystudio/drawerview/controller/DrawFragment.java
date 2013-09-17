package com.gmail.at.gerystudio.drawerview.controller;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gmail.at.gerystudio.drawerview.R;
import com.gmail.at.gerystudio.drawerview.view.DrawView;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-14
 * Time: AM11:40
 * To change this template use File | Settings | File Templates.
 */
public class DrawFragment extends Fragment {

    private static final String LOG_TAG = "Drawer";

    private SensorManager sensorManager;

    private DrawView drawView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_draw, container, false);
        drawView = (DrawView) v.findViewById(R.id.drawview);
        return v;
    }


    private final SensorEventListener sensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            float accuracy = (float) Math.sqrt((double) (x*x + y*y + z*z));
//            Log.d(LOG_TAG, String.format("accuracy is %.2f", accuracy));
            if (accuracy >= 25) {
                if (drawView != null) {
                    drawView.clear();
                }
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
        if (sensorManager == null) {
            sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        }
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDetach() {
        super.onDetach();    //To change body of overridden methods use File | Settings | File Templates.
        sensorManager.unregisterListener(sensorListener);
    }
}
