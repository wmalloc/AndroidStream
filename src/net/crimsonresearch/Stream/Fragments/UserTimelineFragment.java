package net.crimsonresearch.Stream.Fragments;

import android.os.Bundle;

public class UserTimelineFragment extends TimelineFragment {
	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setResource("statuses/user_timeline.json");
	}
}
