package com.example.your_contact.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.your_contact.R;

public class ShapeFragment extends Fragment {

    View mView;
    RadioButton red, green, yellow, blue, orange, purple;
    ImageView starI, car, flower, moto, style;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.shape_fragment, container, false);
        starI = mView.findViewById(R.id.star);
        car = mView.findViewById(R.id.car);
        flower = mView.findViewById(R.id.flower);
        moto = mView.findViewById(R.id.moto);
        style = mView.findViewById(R.id.style);

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
                    purple.setChecked(false);
                    orange.setChecked(false);
                }
            }
        });
        green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("green");
                    purple.setChecked(false);
                    orange.setChecked(false);
                }
            }
        });

        yellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("yellow");
                    purple.setChecked(false);
                    orange.setChecked(false);
                }
            }
        });
        blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("blue");
                    purple.setChecked(false);
                    orange.setChecked(false);
                }
            }
        });
        orange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("orange");
                    blue.setChecked(false);
                    red.setChecked(false);
                    green.setChecked(false);
                    yellow.setChecked(false);
                }
            }
        });
        purple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeColor("purple");
                    blue.setChecked(false);
                    red.setChecked(false);
                    green.setChecked(false);
                    yellow.setChecked(false);
                }
            }
        });

        return mView;
    }

    public void changeColor(String color) {
        int red = getActivity().getColor(R.color.red);
        int green = getActivity().getColor(R.color.green);
        int blue = getActivity().getColor(R.color.blue);
        int yellow = getActivity().getColor(R.color.yellow);
        int orange = getActivity().getColor(R.color.orange);
        int purple = getActivity().getColor(R.color.purple);
        switch (color) {
            case "red":
                setColor(red);
                break;
            case "green":
                setColor(green);

                break;
            case "yellow":
                setColor(yellow);
                break;
            case "blue":
                setColor(blue);
                break;
            case "orange":
                setColor(orange);
                break;
            case "purple":
                setColor(purple);
                break;
        }
    }

    private void setColor(int color) {
        starI.setColorFilter(color);
        car.setColorFilter(color);
        moto.setColorFilter(color);
        style.setColorFilter(color);
        flower.setColorFilter(color);
    }
}
