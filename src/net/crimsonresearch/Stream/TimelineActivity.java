package net.crimsonresearch.Stream;

import net.crimsonresearch.Stream.Fragments.HomeTimelineFragment;
import net.crimsonresearch.Stream.Fragments.MentionsTimelineFragment;
import net.crimsonresearch.Stream.models.User;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity implements TabListener {
	public static final int COMPOSE_ACTIVITY = 1;
	public static final String SCREEN_NAME_KEY="screen_name";	
	User loggedInUser = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
		getUserInfo();
	}
	
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home");
		tabHome.setTag("HomeTimelineFragment");
		tabHome.setIcon(R.drawable.ic_action_home);
		tabHome.setTabListener(this);
		
		Tab mentionsTab = actionBar.newTab().setText("Mentions");
		mentionsTab.setTag("MentionsTimelineFragment");
		mentionsTab.setIcon(R.drawable.ic_action_mentions);
		mentionsTab.setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(mentionsTab);
		actionBar.selectTab(tabHome);
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
	            onComposeActivity();
	            return true;
	    	case R.id.action_profile:
	    		onProfileActivity(loggedInUser);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void onComposeActivity() {
		Intent i = new Intent(getApplicationContext(), ComposeTweet.class);
		i.putExtra(SCREEN_NAME_KEY, loggedInUser.getScreenName());
		startActivityForResult(i, COMPOSE_ACTIVITY);
	}
	
	private void onProfileActivity(User user) {
		Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
		i.putExtra(ProfileActivity.USER_PROFILE_KEY, Long.toString(user.getIdentifier()));
		startActivity(i);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == COMPOSE_ACTIVITY && resultCode == Activity.RESULT_OK) {
			//customLoadMoreDataFromApi(0);
		}
	}
	
    public void getUserInfo() {
		StreamClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject jsonUser) {
				loggedInUser = new User(jsonUser);
				setTitle("@" + loggedInUser.getScreenName());
			}
			
            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
		});
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = fragmentManager.beginTransaction();
		if(tab.getTag() == "HomeTimelineFragment") {
			fts.replace(R.id.frameContainer, new HomeTimelineFragment());
		} else {
			fts.replace(R.id.frameContainer, new MentionsTimelineFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
}
