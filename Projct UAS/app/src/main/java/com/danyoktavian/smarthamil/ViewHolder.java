package com.danyoktavian.smarthamil;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    WebView webView;
    TextView tvDescription;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        webView = itemView.findViewById(R.id.webView);
        tvDescription = itemView.findViewById(R.id.tv_description);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void setDescription(String description) {
        tvDescription.setText(description);
    }
}