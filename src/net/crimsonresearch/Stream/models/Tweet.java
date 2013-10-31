package net.crimsonresearch.Stream.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable {
	private static final long serialVersionUID = -2918583239683010830L;
	private JSONObject _tweet;
	private User _user;
	private long _identifier;
	private boolean _favorited;
	private String _createdAt;
	private boolean _isRetweeted;
	private String _body;

	public Tweet(JSONObject jsonObject) {
		try {
			setTweet(jsonObject);
			setUser(new User(jsonObject.getJSONObject("user")));
	        setIdentifier(jsonObject.getLong("id"));
	        setFavorited(jsonObject.getBoolean("favorited"));
	        setCreatedAt(jsonObject.getString("created_at"));
	        setRetweeted(jsonObject.getBoolean("retweeted"));
	        setBody(jsonObject.getString("text"));
		} catch (JSONException e) {
			e.printStackTrace();
	     }
	}
	
    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = new Tweet(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }

	public User getUser() {
		return _user;
	}

	public void setUser(User user) {
		this._user = user;
	}

	public long getIdentifier() {
		return _identifier;
	}

	public void setIdentifier(long identifier) {
		this._identifier = identifier;
	}

	public boolean isFavorited() {
		return _favorited;
	}

	public void setFavorited(boolean favorited) {
		this._favorited = favorited;
	}

	public String getCreatedAt() {
		return _createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this._createdAt = createdAt;
	}

	public boolean isRetweeted() {
		return _isRetweeted;
	}

	public void setRetweeted(boolean isRetweeted) {
		_isRetweeted = isRetweeted;
	}

	public String getBody() {
		return _body;
	}

	public void setBody(String body) {
		this._body = body;
	}

	public JSONObject getTweet() {
		return _tweet;
	}

	public void setTweet(JSONObject _tweet) {
		this._tweet = _tweet;
	}
}