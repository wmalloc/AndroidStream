package net.crimsonresearch.Stream;

import java.util.ArrayList;

import net.crimsonresearch.Stream.models.Tweet;
import net.crimsonresearch.Stream.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	public static final int COMPOSE_ACTIVITY = 1;
	public static final String SCREEN_NAME_KEY="screen_name";
	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	long lastId = 0;
	User loggedInUser = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
		        customLoadMoreDataFromApi(page); 
		    }
	    });
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Your code to refresh the list contents
				// Make sure you call listView.onRefreshComplete()
				// once the loading is done. This can be done from here or any
				// place such as when the network request has completed successfully.
				customLoadMoreDataFromApi(0);
			}
		});
		customLoadMoreDataFromApi(0);
		getUserInfo();
	}

	private void customLoadMoreDataFromApi(int page) {
		if(page == 0) {
			lastId = 0;
			try {
				adapter.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		StreamClientApp.getRestClient().getHomeTimeline(lastId, new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				int count = tweets.size();
				try {
					Tweet lastTweet =  tweets.get(count - 1);
					lastId = lastTweet.getIdentifier();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(null == adapter) {
					adapter = new TweetsAdapter(getBaseContext(), tweets);
					lvTweets.setAdapter(adapter);
				} else {
					adapter.addAll(tweets);
				}
				
				lvTweets.onRefreshComplete();
			}
			
            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	    	case R.id.action_compose:
	            ComposeTweet();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void ComposeTweet() {
		Intent i = new Intent(getApplicationContext(), ComposeTweet.class);
		i.putExtra(SCREEN_NAME_KEY, loggedInUser.getScreenName());
		startActivityForResult(i, COMPOSE_ACTIVITY);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == COMPOSE_ACTIVITY && resultCode == Activity.RESULT_OK) {
			customLoadMoreDataFromApi(0);
		}
	}
	
    public void getUserInfo() {
		StreamClientApp.getRestClient().getAccountSettings(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject jsonUser) {
				loggedInUser = new User(jsonUser);
				System.out.println(jsonUser.toString());
				setTitle("@" + loggedInUser.getScreenName());
			}
			
            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
		});
    }
}
