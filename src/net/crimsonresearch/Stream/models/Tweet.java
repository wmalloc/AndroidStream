package net.crimsonresearch.Stream.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable {
	private static final long serialVersionUID = -2918583239683010830L;
	JSONObject tweet;
	private User user;
	private long identifier;
	private boolean favorited;
	private String createdAt;
	private boolean isRetweeted;
	private String body;

	public Tweet(JSONObject jsonObject) {
		try {
			tweet = jsonObject;
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
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isRetweeted() {
		return isRetweeted;
	}

	public void setRetweeted(boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}