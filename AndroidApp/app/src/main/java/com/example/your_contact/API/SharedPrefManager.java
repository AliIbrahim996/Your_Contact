package com.example.your_contact.API;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.your_contact.Model.User;


public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private Context mContext;

    private SharedPrefManager(Context mContext) {
        this.mContext = mContext;
    }

    public static synchronized SharedPrefManager getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mContext);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mContext.
                getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", user.getId());
        editor.putString("user_name", user.getUser_name());
        editor.putString("email", user.getEmail());

        editor.apply();


    }

    public User getUser() {
        SharedPreferences sharedPreferences = mContext.
                getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getString("id", ""),
                sharedPreferences.getString("email", ""),sharedPreferences.getString("user_name", "")
                );

    }


    //logout
    public void Clear() {
        SharedPreferences sharedPreferences = mContext.
                getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
