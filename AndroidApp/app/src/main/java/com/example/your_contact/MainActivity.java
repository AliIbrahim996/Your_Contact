package com.example.your_contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.your_contact.API.SharedPrefManager;
import com.example.your_contact.fragments.AccountFragment;
import com.example.your_contact.fragments.ChatFragment;
import com.example.your_contact.fragments.DrawFragment;
import com.example.your_contact.fragments.MathFragment;
import com.example.your_contact.fragments.TaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    String email, full_name, ip;
    Bundle b;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.account:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new AccountFragment())
                            .commit();
                    return true;
                case R.id.draw:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new DrawFragment())
                            .commit();
                    return true;
                case R.id.tasks:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new TaskFragment())
                            .commit();
                    return true;
                case R.id.math:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new MathFragment())
                            .commit();
                    return true;
                case R.id.chat:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_frag, new ChatFragment())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        SharedPrefManager.getInstance(getApplicationContext())
                .Clear();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.main_frag);
        navigation.setItemIconTintList(null);
        ImageView logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext())
                        .Clear();
                finish();
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new AccountFragment())
                    .commit();
            navigation.setSelectedItemId(R.id.account);
        }

    }
}
