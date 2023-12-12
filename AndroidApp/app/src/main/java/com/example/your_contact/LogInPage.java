package com.example.your_contact;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.your_contact.API.LogInResponse;
import com.example.your_contact.API.RetrofitClient;
import com.example.your_contact.API.SharedPrefManager;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInPage extends AppCompatActivity {

    EditText user_email, user_password;
    MaterialButton login_button, singupBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(user_email.getText().toString())) {
                    user_email.setError("ادخل البريد الإلكتروني!");
                    user_email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(user_password.getText().toString())) {
                    user_password.setError("الرجاء ادخال كلمة المرور!");
                    user_password.requestFocus();
                    return;
                }

                try {
                    RetrofitClient
                            .getInstance()
                            .getApi()
                            .logInUser(user_email.getText().toString(), user_password.getText().toString())
                            .enqueue(new Callback<LogInResponse>() {
                                @Override
                                public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                                    if(response.code() == 200){
                                        SharedPrefManager.getInstance(getApplicationContext())
                                                .saveUser(response.body().getUser());
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                    else {
                                        Log.e("error","Response code: "+response.code()+ "\n"+response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<LogInResponse> call, Throwable t) {
                                    Log.e("OnFailure",t.getMessage());
                                }
                            });
                } catch (Exception e) {

                }
            }
        });
        singupBtn = findViewById(R.id.singupBtn);

        singupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SingUpScreen.class));
            }
        });
    }
}
