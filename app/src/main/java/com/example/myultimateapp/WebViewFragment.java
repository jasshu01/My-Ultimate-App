package com.example.myultimateapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


public class WebViewFragment extends Fragment {



    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_web_view, container, false);

        WebView webView=view.findViewById(R.id.webView);
        EditText url=view.findViewById(R.id.webViewEnterURL);
        Button btn=view.findViewById(R.id.webViewGoBtn);
        url.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        webView.loadUrl("google.com");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient());

        return view;

    }
}