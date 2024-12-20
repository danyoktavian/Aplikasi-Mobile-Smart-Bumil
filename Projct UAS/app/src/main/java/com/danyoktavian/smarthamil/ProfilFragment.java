package com.danyoktavian.smarthamil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;

public class ProfilFragment extends Fragment {

    private TextView userNameTextView;
    private TextView userAccountTextView;
    private ImageView profileImageView;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        // Inisialisasi FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Inisialisasi TextView dan ImageView
        userNameTextView = view.findViewById(R.id.user_name);
        userAccountTextView = view.findViewById(R.id.user_account);
        profileImageView = view.findViewById(R.id.profile_picture);

        // Mendapatkan pengguna saat ini
        FirebaseUser user = mAuth.getCurrentUser();

        // Memeriksa apakah pengguna telah masuk
        if (user != null) {
            // Mendapatkan nama pengguna dan email
            String userName = user.getDisplayName();
            String userEmail = user.getEmail();
            String userProfileUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;

            // Menetapkan nama pengguna dan email ke TextView
            userNameTextView.setText(userName);
            userAccountTextView.setText(userEmail);

            // Memuat gambar profil menggunakan AsyncTask
            if (userProfileUrl != null) {
                new DownloadImageTask(profileImageView).execute(userProfileUrl);
            } else {
                // Atur gambar default jika pengguna tidak memiliki foto profil
                profileImageView.setImageResource(R.drawable.user);
            }
        } else {
            // Jika pengguna belum masuk, menampilkan pesan atau mengarahkan mereka ke layar masuk
            userNameTextView.setText("Guest");
            userAccountTextView.setText("guest@example.com");
            profileImageView.setImageResource(R.drawable.user);
            // Misalnya, Anda dapat menampilkan dialog atau mengarahkan mereka ke layar masuk:
            // startActivity(new Intent(getContext(), LoginActivity.class));
        }

        // TextView untuk logout
        TextView logoutTextView = view.findViewById(R.id.logout);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil fungsi logout
                logout();
            }
        });

        return view;
    }

    // Kelas AsyncTask untuk mengunduh gambar dari URL
    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            } else {
                // Atur gambar default jika pengunduhan gambar gagal
                imageView.setImageResource(R.drawable.user);
            }
        }
    }

    // Fungsi untuk melakukan log out
    private void logout() {
        mAuth.signOut();
        // Redirect ke halaman login atau home (sesuai kebutuhan)
        // Misalnya:
        startActivity(new Intent(getContext(), LoginActivity.class));
        Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
    }
}