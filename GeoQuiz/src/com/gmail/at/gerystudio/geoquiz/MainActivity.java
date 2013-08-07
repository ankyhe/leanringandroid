package com.gmail.at.gerystudio.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button previousButton;
    private TextView textView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        trueButton = (Button)findViewById(R.id.true_button);
        falseButton = (Button)findViewById(R.id.false_button);
        nextButton = (Button)findViewById(R.id.next_button);
        previousButton = (Button)findViewById(R.id.previous_button);
        textView = (TextView)findViewById(R.id.textview);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, checkValue(true), Toast.LENGTH_SHORT).show();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, checkValue(false), Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question q = QuestionPool.getInstance().next();
                textView.setText(q.questionStr);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question q = QuestionPool.getInstance().previous();
                textView.setText(q.questionStr);
            }
        });

        Question q = QuestionPool.getInstance().next();
        textView.setText(q.questionStr);
    }

    private int checkValue(boolean trueOrFalse) {
        if (QuestionPool.getInstance().isCorrect(trueOrFalse)) {
            return R.string.correct_toast;
        } else {
            return R.string.wrong_toast;
        }
    }
}
