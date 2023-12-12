package com.example.your_contact.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.your_contact.R;

public class DrawFragment extends Fragment {
    RadioButton numbers, shapes;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.draw_fragment, container, false);

        numbers = mView.findViewById(R.id.numbers);
        shapes = mView.findViewById(R.id.shapes);
        FragmentManager manager = getFragmentManager();
        numbers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    manager.beginTransaction()
                            .replace(R.id.draw_frag, new NumberFragment())
                            .commit();
                }
            }

        });
        shapes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    manager.beginTransaction()
                            .replace(R.id.draw_frag, new ShapeFragment())
                            .commit();
            }
        });
        return mView;


    }
}
