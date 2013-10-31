package net.crimsonresearch.Stream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;

// http://twitter.com/screenName/status/identifier	
@SuppressLint("SetJavaScriptEnabled")
public class ViewTweetActivity extends Activity {
	public static String TWEET_VIEW_SCREENNAME_KEY = "screenName";
	public static String TWEET_VIEW_IDENTIFIER_KEY = "identifier";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_tweet);
		
		WebView webView = (WebView) findViewById(R.id.wvTweet);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new TweetWebViewClient());
		String screenName = getIntent().getStringExtra(TWEET_VIEW_SCREENNAME_KEY);
		String identifier = getIntent().getStringExtra(TWEET_VIEW_IDENTIFIER_KEY);
		String url = "http://twitter.com/" + screenName + "/status/" + identifier;
		Log.d("DEBUG", url);
		webView.loadUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_tweet, menu);
		return true;
	}

}
