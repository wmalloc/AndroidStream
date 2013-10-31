package net.crimsonresearch.Stream;

import net.crimsonresearch.Stream.Fragments.TimelineFragment.OnTimelineSelectedListener;
import net.crimsonresearch.Stream.Fragments.UserTimelineFragment;
import net.crimsonresearch.Stream.models.Tweet;
import net.crimsonresearch.Stream.models.User;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity implements OnTimelineSelectedListener {
	public static final String USER_PROFILE_KEY="user";
	private User _user = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String user_id = (String) getIntent().getStringExtra(USER_PROFILE_KEY);
		FragmentManager fragmentManager = getSupportFragmentManager();
		UserTimelineFragment fragment = (UserTimelineFragment) fragmentManager.findFragmentById(R.id.fragmentUserTimeline);
		fragment.setUserId(user_id);
		StreamClientApp.getRestClient().getUserForIdentifier(user_id, new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject jsonUser) {
				_user = new User(jsonUser);
				setTitle("@" + _user.getScreenName());
				populateProfileHeader(_user);
			}
			
            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	private void populateProfileHeader(User user) {
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		TextView tvProfileName = (TextView) findViewById(R.id.tvProfileName);
		TextView tvTagLine = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowings);
		
		tvProfileName.setText(user.getName());
		tvTagLine.setText(user.getTagLine());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
	}
	
	public User getUser() {
		return _user;
	}

	public void setUser(User user) {
		this._user = user;
	}

	@Override
	public void onTweetSelected(Tweet tweet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onImageSelected(String identifier) {
		// TODO Auto-generated method stub
		
	}

}
