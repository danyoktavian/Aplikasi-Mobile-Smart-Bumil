package com.danyoktavian.smarthamil;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setBackgroundColor(getResources().getColor(R.color.utama)); // Mengubah warna menjadi pink

        setSupportActionBar(topAppBar);

        // Ubah warna tombol navigasi menjadi hitam
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int color = ContextCompat.getColor(this, R.color.utama);
            getWindow().setNavigationBarColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
                controller.setAppearanceLightNavigationBars(false);
            } else {
                @SuppressWarnings("deprecation")
                int flags = getWindow().getDecorView().getSystemUiVisibility();
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                getWindow().getDecorView().setSystemUiVisibility(flags);
            }
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //frameLayout = findViewById(R.id.frameLayout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new Panduan_KehamilanFragment());
        fragmentTransaction.commit();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new BerandaFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.beranda);  // Set item menu beranda sebagai yang dipilih
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.panduan_kehamilan) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new Panduan_KehamilanFragment());
                    fragmentTransaction.commit();

                } else if (item.getItemId()==R.id.pelacak_kesehatan){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new Pelacak_KesehatanFragment());
                    fragmentTransaction.commit();
                } else if (item.getItemId()==R.id.beranda){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new BerandaFragment());
                    fragmentTransaction.commit();
                } else if (item.getItemId()==R.id.forum_komunitas){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new Forum_KomunitasFragment());
                    fragmentTransaction.commit();
                } else if (item.getItemId()==R.id.profil){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new ProfilFragment());
                    fragmentTransaction.commit();
                }

                return true;
            }
        });
    }
}