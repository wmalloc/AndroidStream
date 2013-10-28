package net.crimsonresearch.Stream;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class StreamClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CALLBACK_URL = "crimson://oauth"; // Change this (here and in manifest)
    public static final int REST_DEFAULT_COUNT = 25;
    
    public StreamClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(long since_id, AsyncHttpResponseHandler handler) {
        getTimeline("statuses/home_timeline.json", since_id, handler);
    }
    
    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
    	RequestParams params = new RequestParams();
    	params.put("status", tweet);
    	postResource("statuses/update.json", params, handler);
     }
    
    public void getAccountSettings(AsyncHttpResponseHandler handler) {
    	getResource("account/settings.json", null, handler);
    }

    public void lookupUserForScreenName(String username, AsyncHttpResponseHandler handler) {
    	RequestParams params = new RequestParams();	
    	params.put("screen_name", username);
    	getResource("users/lookup.json", params, handler);
    }
    
    public void lookupUserForIdentifier(String identifier, AsyncHttpResponseHandler handler) {
    	RequestParams params = new RequestParams();
    	params.put("user_id", identifier);
    	getResource("users/lookup.json", params, handler);
    }
    
    public void getMentions(long since_id, AsyncHttpResponseHandler handler ) {
        getTimeline("statuses/mentions_timeline.json", since_id, handler);
    }

    public void getTimeline(String time_line, long since_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("count", String.valueOf(REST_DEFAULT_COUNT));
        if(since_id > 0) {
            params.put("since_id", String.valueOf(since_id));
        }
        getResource(time_line, params, handler);
    }

    public void getResource(String resource, RequestParams params, AsyncHttpResponseHandler handler) {
        String apiURL = getApiUrl(resource);
        client.get(apiURL, params, handler);
    }
    
    public void putResource(String resource,  RequestParams params, AsyncHttpResponseHandler handler) {
    	String apiURL = getApiUrl(resource);
    	client.put(apiURL, params, handler);
    }
    
    public void postResource(String resource, RequestParams params, AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl(resource);
    	client.post(apiUrl, params, handler);
    }
}