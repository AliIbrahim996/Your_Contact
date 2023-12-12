package com.example.your_contact.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.your_contact.API.RetrofitClient;
import com.example.your_contact.API.SharedPrefManager;
import com.example.your_contact.API.UsersResponse;
import com.example.your_contact.Adapter.UserAdapter;
import com.example.your_contact.R;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {
    View mView;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.chat_fragment, container, false);

        recyclerView = mView.findViewById(R.id.users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));

        try {
            RetrofitClient
                    .getInstance()
                    .getApi()
                    .getUsers(SharedPrefManager.getInstance(mView.getContext()).getUser().getId())
                    .enqueue(new Callback<UsersResponse>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                            if (response.code() == 200) {
                                recyclerView.setAdapter(
                                        new UserAdapter(mView.getContext(), response.body().getUsers())
                                );
                            } else {
                                Log.e("Error", "Code: " + response.code() + "\n body: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                            Log.e("OnFailure", t.getMessage());

                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mView;
    }
}
