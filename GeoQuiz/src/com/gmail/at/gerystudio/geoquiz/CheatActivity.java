package com.gmail.at.gerystudio.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-10
 * Time: AM10:56
 * To change this template use File | Settings | File Templates.
 */
public class CheatActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheat);

        Intent data = new Intent();
        data.putExtra("showanswer", false);
        setResult(RESULT_OK, data);

        final TextView textView = (TextView)findViewById(R.id.show_answer_textview);
        Button showButton = (Button)findViewById(R.id.show_button);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getBooleanExtra("answer", false


                )) {
                    textView.setText("True");
                } else {
                    textView.setText("False");
                }
                Intent data = new Intent();
                data.putExtra("showanswer", true);
                setResult(RESULT_OK, data);
            }
        });
    }
}