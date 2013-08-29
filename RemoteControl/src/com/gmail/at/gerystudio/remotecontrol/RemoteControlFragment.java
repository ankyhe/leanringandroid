package com.gmail.at.gerystudio.remotecontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 8/29/13
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoteControlFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remotecontrol, container, false);

        final TextView selectedTextView = (TextView)v
                .findViewById(R.id.fragment_remote_control_selectedTextView);
        final TextView workingTextView = (TextView)v
                .findViewById(R.id.fragment_remote_control_workingTextView);

        View.OnClickListener numberButtonListener = new View.OnClickListener() {
            public void onClick(View v) {
                TextView textView = (TextView)v;
                String working = workingTextView.getText().toString();
                String text = textView.getText().toString();
                if (working.equals("0")) {
                    workingTextView.setText(text);
                } else {
                    workingTextView.setText(working + text);
                }
            }
        };


        TableLayout tableLayout = (TableLayout)v
                .findViewById(R.id.tablelayout);
        int number = 1;
        for (int i = 2; i < tableLayout.getChildCount() - 1; i++) {
            TableRow row = (TableRow)tableLayout.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                Button button = (Button)row.getChildAt(j);
                button.setText("" + number);
                button.setOnClickListener(numberButtonListener);
                number++;
            }
        }

        TableRow bottomRow = (TableRow)tableLayout
                .getChildAt(tableLayout.getChildCount() - 1);

        Button deleteButton = (Button)bottomRow.getChildAt(0);
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                workingTextView.setText("0");
            }
        });

        Button zeroButton = (Button)bottomRow.getChildAt(1);
        zeroButton.setText("0");
        zeroButton.setOnClickListener(numberButtonListener);

        Button enterButton = (Button)bottomRow.getChildAt(2);
        enterButton.setText(getString(R.id.enter_button));

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTextView.setText(workingTextView.getText());
                workingTextView.setText("0");
            }
        });

        Button delButton = (Button)bottomRow.getChildAt(0);
        delButton.setText(getString(R.id.del_button));

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workingTextView.getText().equals("0")) {
                    return;
                }

                StringBuilder sb = new StringBuilder(workingTextView.getText());
                workingTextView.setText(sb.subSequence(0, sb.length() - 1));
            }
        });


        return v;
    }
}
