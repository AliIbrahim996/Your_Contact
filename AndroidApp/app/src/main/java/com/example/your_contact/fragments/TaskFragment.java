package com.example.your_contact.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.your_contact.API.DefaultResponse;
import com.example.your_contact.API.RetrofitClient;
import com.example.your_contact.API.SharedPrefManager;
import com.example.your_contact.API.TaskResponse;
import com.example.your_contact.Adapter.TaskAdapter;
import com.example.your_contact.R;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskFragment extends Fragment {

    RecyclerView recyclerView;
    MaterialButton addTask, add;
    TaskAdapter adapter;
    Dialog add_taskDialog;
    EditText task, content;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.task_fragment, container, false);
        recyclerView = mView.findViewById(R.id.recyclerView);
        addTask = mView.findViewById(R.id.addTask);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        add_taskDialog = new Dialog(mView.getContext());
        add_taskDialog.setContentView(R.layout.add_task_dialog);
        task = add_taskDialog.findViewById(R.id.task);
        content = add_taskDialog.findViewById(R.id.content);
        add = add_taskDialog.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(task.getText().toString())) {
                    task.setError("ادخل اسم لمهمتك");
                    task.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(content.getText().toString())) {
                    content.setError("ادخل محتوى");
                    content.requestFocus();
                    return;
                }
                add_taskDialog.dismiss();
                try {
                    System.out.println("Data: " + SharedPrefManager
                            .getInstance(mView.getContext())
                            .getUser().getId() + " " +
                            task.getText().toString() + "  " +
                            content.getText()
                                    .toString());
                    RetrofitClient.getInstance()
                            .getApi()
                            .addTask(SharedPrefManager
                                            .getInstance(mView.getContext())
                                            .getUser().getId(),
                                    task.getText().toString(),
                                    content.getText()
                                            .toString())
                            .enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    if (response.code() == 201) {
                                        Toast.makeText(mView.getContext(), response.body().getMsg(), Toast.LENGTH_LONG)
                                                .show();
                                        task.setText("");
                                        content.setText("");
                                        getTasks();
                                    } else {
                                        Log.e("Error", "Code: " + response.code() + "\n body: " + response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                    Log.e("OnFailure", t.getMessage());
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_taskDialog.show();
            }
        });

        getTasks();
        return mView;
    }

    private void getTasks() {
        try {
            RetrofitClient.getInstance()
                    .getApi()
                    .getAllTasks(SharedPrefManager.getInstance(mView.getContext())
                            .getUser().getId())
                    .enqueue(new Callback<TaskResponse>() {
                        @Override
                        public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                            if (response.code() == 200) {
                                adapter = new TaskAdapter(response.body().getTasks(), mView.getContext());
                                recyclerView.setAdapter(adapter);
                            } else {
                                Log.e("Error", "Code: " + response.code() + "\n body: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<TaskResponse> call, Throwable t) {
                            Log.e("OnFailure", t.getMessage());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
