package com.example.your_contact;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.your_contact.API.DefaultResponse;
import com.example.your_contact.API.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingUpScreen extends AppCompatActivity {
    EditText edit_full_name, edit_email, edit_password;
    MaterialButton button;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        edit_full_name = findViewById(R.id.edit_full_name);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        button = findViewById(R.id.button);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit_full_name.getText().toString())) {
                    edit_full_name.setError("اسم المستخدم مطلوب");
                    edit_full_name.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(edit_email.getText().toString())) {
                    edit_full_name.setError("البريد الإلطتروني مطلوب");
                    edit_full_name.requestFocus();
                    return;

                }

                if (!Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()) {
                    edit_full_name.setError("الرجاء ادخال بريد الكتروني صحيح");
                    edit_full_name.requestFocus();
                    return;

                }

                if (TextUtils.isEmpty(edit_password.getText().toString())) {
                    edit_password.setError("كلمة المرور مطلوبة!");
                    edit_password.requestFocus();
                    return;

                }

                try {
                    RetrofitClient.getInstance()
                            .getApi().createUser(edit_full_name.getText().toString(), edit_email.getText().toString(),
                            edit_password.getText().toString())
                            .enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    if (response.code() == 201) {
                                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG)
                                                .show();
                                        finish();
                                    } else {
                                        /*Toast.makeText(getApplicationContext(), "something went wrong please try again!", Toast.LENGTH_LONG)
                                                .show();*/
                                        Log.e("Error", "Response code: " + response.code() + "\n" + response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                    Log.e("onFailure", t.getMessage());

                                }
                            });
                } catch (Exception ex) {

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
