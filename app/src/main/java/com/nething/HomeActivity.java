package com.nething;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
   BottomNavigationView BottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();

        BottomNavigationView = findViewById(R.id.bottomNavigationView);

        BottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()){
                case R.id.menu:
                    transaction1.replace(R.id.container,new MenuFragment());
                    break;

                case R.id.home:
                    transaction1.replace(R.id.container,new HomeFragment());
                    break;

                case R.id.profile:
                    transaction1.replace(R.id.container,new profileFragment());
                    break;
            }
            transaction1.commit();
            return true;
        });
    }
}