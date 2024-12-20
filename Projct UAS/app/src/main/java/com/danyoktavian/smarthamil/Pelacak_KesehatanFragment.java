package com.danyoktavian.smarthamil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Pelacak_KesehatanFragment extends Fragment {
    private EditText editTextBirthTracker;
    private TextView textViewDueDate;
    private Button buttonPredict;
    private EditText editTextImportantReminder;
    private Button buttonSaveReminder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_pelacak_kesehatan, container, false);

        editTextBirthTracker = myview.findViewById(R.id.editTextBirthTracker);
        textViewDueDate = myview.findViewById(R.id.textViewDueDate);
        buttonPredict = myview.findViewById(R.id.buttonPredict);
        editTextImportantReminder = myview.findViewById(R.id.editTextImportantReminder);
        buttonSaveReminder = myview.findViewById(R.id.buttonSaveReminder);

        // Load data from Shared Preferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        editTextBirthTracker.setText(sharedPreferences.getString("birthStartDate", ""));
        editTextImportantReminder.setText(sharedPreferences.getString("importantReminder", ""));

        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predictDueDate();
            }
        });

        buttonSaveReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the reminder to Shared Preferences
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("Pengingat", getActivity().MODE_PRIVATE).edit();
                editor.putString("importantReminder", editTextImportantReminder.getText().toString());
                editor.apply();

                Toast.makeText(getActivity(), "Pengingat berhasil disimpan.", Toast.LENGTH_SHORT).show();
            }
        });

        return myview;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save data to Shared Preferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("birthStartDate", editTextBirthTracker.getText().toString());
        editor.putString("importantReminder", editTextImportantReminder.getText().toString());
        editor.apply();
    }

    private void predictDueDate() {
        String birthTracker = editTextBirthTracker.getText().toString().trim();

        if (TextUtils.isEmpty(birthTracker)) {
            Toast.makeText(getActivity(), "Masukkan tanggal awal siklus menstruasi Anda.", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            Date startDate = sdf.parse(birthTracker);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            // Tambahkan 280 hari (40 minggu) untuk mendapatkan tanggal perkiraan lahir
            calendar.add(Calendar.DAY_OF_YEAR, 280);

            String dueDate = sdf.format(calendar.getTime());
            textViewDueDate.setText("Tanggal Perkiraan Lahir: " + dueDate);

            Toast.makeText(getActivity(), "Prediksi berhasil. Tanggal perkiraan lahir telah dihitung.", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Format tanggal tidak valid.", Toast.LENGTH_SHORT).show();
        }
    }
}
