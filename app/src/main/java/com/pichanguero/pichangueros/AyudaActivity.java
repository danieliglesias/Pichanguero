package com.pichanguero.pichangueros;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.pichanguero.pichangueros.R;


public class AyudaActivity extends Activity {
WebView myWebView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda);


        myWebView = (WebView) this.findViewById(R.id.webView);
        myWebView.loadUrl("http://10.0.2.2/test/ayuda.html");

    }

}
