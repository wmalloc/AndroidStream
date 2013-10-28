package net.crimsonresearch.Stream.Fragments;

import android.os.Bundle;

public class HomeTimelineFragment extends TimelineFragment {
	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setResource("statuses/home_timeline.json");
	}
}