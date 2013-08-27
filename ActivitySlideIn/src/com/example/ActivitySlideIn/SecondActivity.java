package com.example.ActivitySlideIn;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-19
 * Time: PM10:52
 * To change this template use File | Settings | File Templates.
 */
public class SecondActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(0, R.anim.right_slide_out);
        return;
    }
}
