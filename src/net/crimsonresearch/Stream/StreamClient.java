package net.crimsonresearch.Stream;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
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
    public static final String REST_CONSUMER_KEY = "Your Key";       // Change this
    public static final String REST_CONSUMER_SECRET = "YOUR Secret; // Change this
    public static final String REST_CALLBACK_URL = "crimson://oauth"; // Change this (here and in manifest)
    public static final int REST_DEFAULT_COUNT = 25;
    
    public StreamClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(long since_id, AsyncHttpResponseHandler handler) {
    	String apiURL = getApiUrl("statuses/home_timeline.json");
    	RequestParams params = new RequestParams();
    	params.put("count", String.valueOf(REST_DEFAULT_COUNT));
    	if(since_id > 0)
    	{
    		params.put("since_id", String.valueOf(since_id));
    	}
    	client.get(apiURL, params, handler);
    }
    
    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
    	String apiURL = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams();
    	params.put("status", tweet);
    	client.post(apiURL, params, handler);
    }
    
    public void getAccountSettings(AsyncHttpResponseHandler handler) {
    	String apiURL = getApiUrl("account/settings.json");
    	client.get(apiURL, null, handler);
    }
    
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here   
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}