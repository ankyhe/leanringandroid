package com.gmail.at.hellomoon;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-11
 * Time: PM3:29
 * To change this template use File | Settings | File Templates.
 */
public class HelloMoonFragment extends Fragment {

    private MediaPlayer mplayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon, container);

        Button play = (Button)view.findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mplayer != null && mplayer.isPlaying()) {
                    mplayer.seekTo(0);
                } else {
                    mplayer = MediaPlayer.create(getActivity(), R.raw.test);
                }
                mplayer.start();
            }
        });

        Button stop = (Button)view.findViewById(R.id.stop_button);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mplayer != null) {
                    mplayer.stop();
                    mplayer.release();
                    mplayer = null;
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        if (mplayer != null) {
            mplayer.stop();
            mplayer.release();
            mplayer = null;
        }
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.

    }
}
