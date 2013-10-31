package net.crimsonresearch.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweet extends Activity {	
	public static final String KEY_NAME = "tweet";
    private final int MAX_TWEET_LENGTH = 140;
    
	TextView _tvCharCount;
	EditText _etTweetText;
	ProgressBar _pbPostingTweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		_pbPostingTweet = (ProgressBar) findViewById(R.id.pbPostingTweet);
		_pbPostingTweet.setIndeterminate(true);
		_tvCharCount = (TextView) findViewById(R.id.tvCharCount);
		_tvCharCount.setText(String.valueOf(MAX_TWEET_LENGTH));
		
		_etTweetText = (EditText) findViewById(R.id.etTweetText);
		_etTweetText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MAX_TWEET_LENGTH)});
		_etTweetText.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					int tick = start + count;
					_tvCharCount.setText(String.valueOf(MAX_TWEET_LENGTH - tick));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			public void afterTextChanged(Editable s) {
			}
		});
		
		String screen_name = (String) getIntent().getStringExtra(TimelineActivity.SCREEN_NAME_KEY);
		setTitle(screen_name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

	public void onTweet(View view) {
		_pbPostingTweet.startAnimation(null);
		String tweet = _etTweetText.getText().toString();
		if(null != tweet && tweet.length() > 0) {
			StreamClientApp.getRestClient().postTweet(tweet, new JsonHttpResponseHandler () {
			     @Override
			     public void onSuccess(JSONArray response) {
						Intent i = new Intent();
						setResult(Activity.RESULT_OK, i);
						finish();
			     }
			 
			     @Override
			     public void onFailure(Throwable e, JSONObject errorResponse) {
			    	 Log.d("DEBUG", errorResponse.toString());
			    	 Toast.makeText(getBaseContext(), "Unable to Post", Toast.LENGTH_SHORT).show();		
			     }

			     @Override
			     public void onFinish() {
					  Log.d("DEBUG", "Finish");
			     }
			});
		}
	}
	
	public void onCancel(View view) {
		Intent i = new Intent();
		setResult(Activity.RESULT_CANCELED, i);
		finish();
	}
}
