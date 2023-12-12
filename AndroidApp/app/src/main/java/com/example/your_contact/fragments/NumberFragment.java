package com.example.your_contact.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.your_contact.R;

public class NumberFragment extends Fragment {

    View mView;
    Button minBtn, resetBtn, maxBtn;
    TextView number;
    RadioButton red, green, yellow, blue, orange, purple;
    int counter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.level0, container, false);

        minBtn = mView.findViewById(R.id.minBtn);
        maxBtn = mView.findViewById(R.id.maxtBtn);
        resetBtn = mView.findViewById(R.id.resetBtn);
        number = mView.findViewById(R.id.number);

        resetCounter();

        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 0) {
                    counter--;
                    number.setText(String.valueOf(counter));
                }

            }
        });
        maxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                number.setText(String.valueOf(counter));
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCounter();
            }
        });


        red = mView.findViewById(R.id.red);
        green = mView.findViewById(R.id.green);
        yellow = mView.findViewById(R.id.yellow);
        blue = mView.findViewById(R.id.blue);
        orange = mView.findViewById(R.id.orange);
        purple = mView.findViewById(R.id.purple);

        red.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("red");
                }
            }
        });
        green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("green");
                }
            }
        });

        yellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("yellow");
                }
            }
        });
        blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("blue");
                }
            }
        });
        orange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("orange");
                }
            }
        });
        purple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("purple");
                }
            }
        });

        return mView;
    }

    public void resetCounter() {
        counter = 0;
        number.setText(String.valueOf(counter));
    }

    public void changeColor(String color) {
        switch (color) {
            case "red":
                number.setTextColor(getActivity().getColor(R.color.red));
                break;
            case "green":
                number.setTextColor(getActivity().getColor(R.color.green));
                break;
            case "yellow":
                number.setTextColor(getActivity().getColor(R.color.yellow));
                break;
            case "blue":
                number.setTextColor(getActivity().getColor(R.color.blue));
                break;
            case "orange":
                number.setTextColor(getActivity().getColor(R.color.orange));
                break;
            case "purple":
                number.setTextColor(getActivity().getColor(R.color.purple));
                break;
        }

    }
}
