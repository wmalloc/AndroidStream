package net.crimsonresearch.Stream;

import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.crimsonresearch.Stream.models.User;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity {
	public static final String USER_PROFILE_KEY="user";
	private User user = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		user = (User) getIntent().getSerializableExtra(USER_PROFILE_KEY);
		StreamClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject jsonUser) {
				user = new User(jsonUser);
				setTitle("@" + user.getScreenName());
				populateProfileHeader(user);
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
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
