package net.crimsonresearch.Stream.Fragments;

import java.util.ArrayList;

import net.crimsonresearch.Stream.EndlessScrollListener;
import net.crimsonresearch.Stream.R;
import net.crimsonresearch.Stream.StreamClientApp;
import net.crimsonresearch.Stream.TweetsAdapter;
import net.crimsonresearch.Stream.models.Tweet;
import net.crimsonresearch.Stream.models.User;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineFragment extends Fragment {
	private TweetsAdapter _adapter;
	private Long _lastId = 0L;
	private PullToRefreshListView _lvTweets;
	private String _resource = null;
	private String _userId = null;
	private OnTimelineSelectedListener _listener;
	private ImageView _ivProfile;
	
	public interface OnTimelineSelectedListener {
        public void onTweetSelected(Tweet tweet);
        public void onImageSelected(String identifier);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_timeline, parent, false);
		
		_ivProfile = (ImageView) v.findViewById(R.id.ivTweetProfile);
		_lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		_adapter = new TweetsAdapter(getActivity(), new ArrayList<Tweet>());
		_lvTweets.setAdapter(_adapter);
		return v;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _listener = (OnTimelineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTimelineSelectedListener");
        }
   }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		_lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
		        customLoadMoreDataFromApi(page); 
		    }
	    });
		
		_lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Your code to refresh the list contents
				// Make sure you call listView.onRefreshComplete()
				// once the loading is done. This can be done from here or any
				// place such as when the network request has completed successfully.
				customLoadMoreDataFromApi(0);
			}
		});
		
		_lvTweets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Tweet tweet = _adapter.getItem(position);
				_listener.onTweetSelected(tweet);
			}
		});

		_lvTweets.setOnItemLongClickListener(new OnItemLongClickListener () {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long id) {
				Tweet tweet = _adapter.getItem(position);
				_listener.onImageSelected(Long.toString(tweet.getUser().getIdentifier()));
				return false;
			}
		});
		customLoadMoreDataFromApi(0);
	}

	private void customLoadMoreDataFromApi(int page) {
		if(page == 0) {
			_lastId = (long) 0;
			try {
				_adapter.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RequestParams params = new RequestParams();
		if(_lastId > 0) {
			params.put("since_id", Long.toString(_lastId));
		}
		
		if(null != _userId) {
			params.put("user_id", _userId);
		}
		StreamClientApp.getRestClient().getTimeline(_resource, params, new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				int count = tweets.size();
				try {
					Tweet lastTweet =  tweets.get(count - 1);
					_lastId = lastTweet.getIdentifier();
				} catch (Exception e) {
					e.printStackTrace();
				}
				_adapter.addAll(tweets);
				
				_lvTweets.onRefreshComplete();
			}
			
            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
		});
	}
	    
	public TweetsAdapter getAdapter() {
		return _adapter;
	}

	public void setAdapter(TweetsAdapter adapter) {
		this._adapter = adapter;
	}

	public Long getLastId() {
		return _lastId;
	}

	public void setLastId(Long lastId) {
		this._lastId = lastId;
	}

	public String getResource() {
		return _resource;
	}

	public void setResource(String resource) {
		this._resource = resource;
	}

	public PullToRefreshListView getLvTweets() {
		return _lvTweets;
	}

	public void setLvTweets(PullToRefreshListView lvTweets) {
		this._lvTweets = lvTweets;
	}

	public String getUserId() {
		return _userId;
	}

	public void setUserId(String userId) {
		this._userId = userId;
	}

	public ImageView getIvProfile() {
		return _ivProfile;
	}

	public void setIvProfile(ImageView ivProfile) {
		this._ivProfile = ivProfile;
	}
}
