package com.danyoktavian.smarthamil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class BerandaFragment extends Fragment {

    TextView textViewWelcome;
    ImageView babyImage;
    ImageView summaryImage;
    TextView summaryText;
    TextView summaryDetail;

    private TextView reminderText1;
    private TextView articleTitle1;
    private ImageView articleImage1;
    private TextView articleTitle2;
    private ImageView articleImage2;
    private EditText editTextImportantReminder;
    private ViewPager viewPager;

    // Usia kandungan (contoh, bisa diganti dengan usia kandungan aktual)
    private int pregnancyWeek = 20;

    // Data ringkasan kehamilan
    private String[] weekSummary = {
            "Janin seukuran buah pisang",
            "Janin seukuran buah kelapa",
            // Menambahkan data ringkasan untuk setiap minggu kehamilan
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);

        reminderText1 = rootView.findViewById(R.id.reminderText1);

        //Link aritcle 1
        articleTitle1 = rootView.findViewById(R.id.articleTitle1);
        articleTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://youtu.be/4ZAHX-N-AhM?si=xIAg_KF2Gc5mKrYm";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //Link aritcle 2
        articleTitle2 = rootView.findViewById(R.id.articleTitle2);
        articleTitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://youtu.be/Irtsmdhy3yM?si=pcgUlABeUNEhUFVU";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        // Inisialisasi view
        textViewWelcome = rootView.findViewById(R.id.textViewWelcome);

        // Image 1
        articleImage1 = rootView.findViewById(R.id.articleImage1);
        articleImage1.setImageResource(R.drawable.articletitle1);

        // Image 2
        articleImage2 = rootView.findViewById(R.id.articleImage2);
        articleImage2.setImageResource(R.drawable.articletitle2);

        // Mengambil nama pengguna dari SharedPreferences dan menampilkan pesan "Selamat datang"
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        String username = prefs.getString("username", "Ibu");
        textViewWelcome.setText("Selamat Datang, " + username + "!");

        // Mengambil data pengingat dari pelacak
        SharedPreferences prefs1 = getActivity().getSharedPreferences("Pengingat", getActivity().MODE_PRIVATE);
        String importantReminder = prefs1.getString("importantReminder", "-");
        reminderText1.setText(importantReminder);

        // Inisialisasi ViewPager untuk image slider
        viewPager = rootView.findViewById(R.id.viewPager);

        // Daftar gambar untuk slider
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.minggu4);
        images.add(R.drawable.minggu5);
        images.add(R.drawable.minggu6);

        ImageSliderAdapter adapter = new ImageSliderAdapter(getActivity(), images);
        viewPager.setAdapter(adapter);

        return rootView;
    }
}
