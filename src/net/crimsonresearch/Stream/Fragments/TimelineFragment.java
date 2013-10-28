package net.crimsonresearch.Stream.Fragments;

import java.util.ArrayList;

import net.crimsonresearch.Stream.EndlessScrollListener;
import net.crimsonresearch.Stream.R;
import net.crimsonresearch.Stream.StreamClientApp;
import net.crimsonresearch.Stream.TweetsAdapter;
import net.crimsonresearch.Stream.models.Tweet;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineFragment extends Fragment {
	private TweetsAdapter adapter;
	private Long lastId = 0L;
	private PullToRefreshListView lvTweets;
	private String resource = null;
	private String userId = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_timeline, parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
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
	}

	private void customLoadMoreDataFromApi(int page) {
		if(page == 0) {
			lastId = (long) 0;
			try {
				adapter.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RequestParams params = new RequestParams();
		if(lastId > 0) {
			params.put("since_id", Long.toString(lastId));
		}
		
		if(null != userId) {
			params.put("user_id", userId);
		}
		StreamClientApp.getRestClient().getTimeline(resource, params, new JsonHttpResponseHandler() {
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
					adapter = new TweetsAdapter(getActivity(), tweets);
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
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(TweetsAdapter adapter) {
		this.adapter = adapter;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public PullToRefreshListView getLvTweets() {
		return lvTweets;
	}

	public void setLvTweets(PullToRefreshListView lvTweets) {
		this.lvTweets = lvTweets;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
