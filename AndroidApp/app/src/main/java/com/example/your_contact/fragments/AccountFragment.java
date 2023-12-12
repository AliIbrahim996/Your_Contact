package com.example.your_contact.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.your_contact.API.DefaultResponse;
import com.example.your_contact.API.RetrofitClient;
import com.example.your_contact.API.SharedPrefManager;
import com.example.your_contact.R;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    SharedPrefManager manager;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.account_fragment, container, false);
        EditText name = mView.findViewById(R.id.name);
        EditText email = mView.findViewById(R.id.email);
        manager = SharedPrefManager.getInstance(mView.getContext());
        name.setText(manager.getUser().getUser_name());
        email.setText(manager.getUser().getEmail());
        MaterialButton update = mView.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RetrofitClient.getInstance()
                            .getApi().updateUser(manager.getUser().getId(), name.getText().toString(), email.getText().toString())
                            .enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    if (response.code() == 201) {
                                        Toast.makeText(mView.getContext(), response.body().getMsg(), Toast.LENGTH_LONG)
                                                .show();
                                    } else
                                        Log.e("Error", "code: " + response.code() + "\nBody: " + response.errorBody());
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                    Log.e("onFailure", t.getMessage());
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return mView;
    }
}
