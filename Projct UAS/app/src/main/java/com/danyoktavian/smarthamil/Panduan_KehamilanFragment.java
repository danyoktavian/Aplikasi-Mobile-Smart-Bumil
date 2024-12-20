package com.danyoktavian.smarthamil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Panduan_KehamilanFragment extends Fragment {
    private RecyclerView recyclerView;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panduan_kehamilan, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Menampilkan dialog loading
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();

        String url = "https://raw.githubusercontent.com/danyoktavian/pregnancy-guide/main/content.json"; // Ganti dengan URL JSON Anda

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismissDialog(); // Menutup dialog loading setelah mendapatkan respons
                        try {
                            JSONArray videosJson = response.getJSONArray("videos"); // Ganti "videos" dengan kunci JSON Anda
                            List<Video> videos = new ArrayList<>();
                            for (int i = 0; i < videosJson.length(); i++) {
                                JSONObject videoJson = videosJson.getJSONObject(i);
                                String videoId = videoJson.getString("videoId");
                                String description = videoJson.getString("description");
                                videos.add(new Video(videoId, description));
                            }
                            recyclerView.setAdapter(new Adapter(videos));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Menampilkan Toast jika terjadi kesalahan saat mengurai JSON
                            Toast.makeText(getActivity(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog(); // Menutup dialog loading jika terjadi kesalahan
                        // Menampilkan Toast jika terjadi kesalahan saat mengambil data dari server
                        Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Menambahkan request ke RequestQueue
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

        return view;
    }
}
