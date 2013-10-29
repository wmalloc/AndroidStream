package net.crimsonresearch.Stream;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TweetWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }
}
