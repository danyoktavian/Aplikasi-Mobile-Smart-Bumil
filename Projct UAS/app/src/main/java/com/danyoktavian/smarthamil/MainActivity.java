package com.danyoktavian.smarthamil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private int waktu_loading = 3000; // 3 detik
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Transparent status bar
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);

        // Temukan ProgressBar
        progressBar = findViewById(R.id.progressBar1);
        progressBar.setIndeterminate(true);

        // Handler untuk menunda transisi ke LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Periksa apakah pengguna sudah login atau belum
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    // Jika pengguna sudah login, langsung buka halaman utama (home)
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                } else {
                    // Jika pengguna belum login, buka halaman login
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
                finish();
            }
        }, waktu_loading);
    }
}