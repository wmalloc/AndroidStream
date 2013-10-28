package net.crimsonresearch.Stream.models;

import java.io.Serializable;

import org.json.JSONObject;

public class User implements Serializable {
 	private static final long serialVersionUID = 9168779711685542730L;
 	JSONObject user = null;
	private String name;
    private String screenName;
    private String profileImageUrl;
    private String tagLine;
    private long identifier;
    private int statusesCount;
    private int followersCount;
    private int friendsCount;

    public User(JSONObject json) {
      	user = json;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(int statues_count) {
        this.statusesCount = statues_count;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}
}