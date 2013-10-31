package net.crimsonresearch.Stream.models;

import java.io.Serializable;

import org.json.JSONObject;

public class User implements Serializable {
 	private static final long serialVersionUID = 9168779711685542730L;
 	private JSONObject _user = null;
	private String _name;
    private String _screenName;
    private String _profileImageUrl;
    private String _tagLine;
    private long _identifier;
    private int _statusesCount;
    private int _followersCount;
    private int _friendsCount;

    public User(JSONObject json) {
      	setUser(json);
      	try {
            setScreenName(json.getString("screen_name"));
            setProfileImageUrl(json.getString("profile_image_url"));
            setIdentifier(json.getLong("id"));
            setStatusesCount(json.getInt("statuses_count"));
            setFollowersCount(json.getInt("followers_count"));
            setFriendsCount(json.getInt("friends_count"));
            setName(json.getString("name"));
            setTagLine(json.getString("description"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getScreenName() {
        return _screenName;
    }

    public void setScreenName(String screenName) {
        this._screenName = screenName;
    }

    public String getProfileImageUrl() {
        return _profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this._profileImageUrl = profileImageUrl;
    }

    public long getIdentifier() {
        return _identifier;
    }

    public void setIdentifier(long identifier) {
        this._identifier = identifier;
    }

    public int getStatusesCount() {
        return _statusesCount;
    }

    public void setStatusesCount(int statues_count) {
        this._statusesCount = statues_count;
    }

    public int getFollowersCount() {
        return _followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this._followersCount = followersCount;
    }

    public int getFriendsCount() {
        return _friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this._friendsCount = friendsCount;
    }

	public String getTagLine() {
		return _tagLine;
	}

	public void setTagLine(String tagLine) {
		this._tagLine = tagLine;
	}

	public JSONObject getUser() {
		return _user;
	}

	public void setUser(JSONObject user) {
		this._user = user;
	}
}