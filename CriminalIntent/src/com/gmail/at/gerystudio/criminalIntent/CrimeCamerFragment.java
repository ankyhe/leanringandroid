package com.gmail.at.gerystudio.criminalIntent;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ProgressBar;
import com.gmail.at.gerystudio.criminalIntent.model.Constants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 8/27/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrimeCamerFragment extends Fragment {

    private static final String LOG_TAG = CrimeCamerFragment.class.getName();

    private Camera camera = null;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_camera, container, false);


        final View pb = v.findViewById(R.id.progressbar_container);
        pb.setVisibility(View.INVISIBLE);

        Button takeButton = (Button) v.findViewById(R.id.take_button);
        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camera != null) {
                    camera.takePicture(new Camera.ShutterCallback() {
                                           @Override
                                           public void onShutter() {
                                               pb.setVisibility(View.VISIBLE);
                                           }
                                       },
                            null,
                            new Camera.PictureCallback() {

                                @Override
                                public void onPictureTaken(byte[] bytes, Camera camera) {
                                    // Create a filename
                                    String filename = UUID.randomUUID().toString() + ".jpg";
                                    // Save the jpeg data to disk
                                    FileOutputStream os = null;
                                    boolean success = true;
                                    try {
                                        os = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                                        os.write(bytes);
                                    } catch (Exception e) {
                                        Log.e(LOG_TAG, "Error writing to file " + filename, e);
                                        success = false;
                                    } finally {
                                        try {
                                            if (os != null)
                                                os.close();
                                        } catch (Exception e) {
                                            Log.e(LOG_TAG, "Error closing file " + filename, e);
                                            success = false;
                                        }
                                    }
                                    if (success) {
                                        Intent i = new Intent();
                                        i.putExtra(Constants.PARAM_PHOTO, filename);
                                        Log.i(LOG_TAG, "JPEG saved at " + filename);
                                        getActivity().setResult(Activity.RESULT_OK, i);
                                    } else {
                                        getActivity().setResult(Activity.RESULT_CANCELED);
                                    }
                                    getActivity().finish();
                                }
                            }
                    );
                } else {
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    getActivity().finish();

                }
            }
        });

        SurfaceView surfaceView = (SurfaceView) v.findViewById(R.id.surfaceView);
        final SurfaceHolder holder = surfaceView.getHolder();
        // setType() and SURFACE_TYPE_PUSH_BUFFERS are both deprecated,
        // but are required for Camera preview to work on pre-3.0 devices.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (camera != null) {
                        camera.setPreviewDisplay(holder);
                    }
                } catch (IOException exception) {
                    Log.e(LOG_TAG, "Error setting up preview display", exception);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
                if (camera == null) {
                    return;
                }
                // The surface has changed size; update the camera preview size
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), w, h);
                parameters.setPreviewSize(s.width, s.height);
                s = getBestSupportedSize(parameters.getSupportedPictureSizes(), w, h);
                parameters.setPictureSize(s.width, s.height);
                camera.setParameters(parameters);
                try {
                    camera.startPreview();
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Could not start preview", e);
                    camera.release();
                    camera = null;
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (camera != null) {
                    camera.stopPreview();
                }
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        camera = Camera.open(0);

    }

    @Override
    public void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int width, int height) {

        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                bestSize = s;
                largestArea = area;
            }
        }
        return bestSize;
    }
}
