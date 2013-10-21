package net.crimsonresearch.Stream;

import java.util.List;

import net.crimsonresearch.Stream.models.Tweet;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetsAdapter extends ArrayAdapter<Tweet> {
	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(null == view) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = getItem(position);
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
		
		TextView tvName = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "<b>" + "<small><font-color='#777777'>@" + tweet.getUser().getScreenName() + "</font></small>";
		tvName.setText(Html.fromHtml(formattedName));
		
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		tvBody.setText(Html.fromHtml(tweet.getBody()));
		
		return view;
	}
}
