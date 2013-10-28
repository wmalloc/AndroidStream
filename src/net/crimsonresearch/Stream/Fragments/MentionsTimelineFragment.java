package net.crimsonresearch.Stream.Fragments;

import android.os.Bundle;

public class MentionsTimelineFragment extends TimelineFragment {
	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setResource("statuses/mentions_timeline.json");
	}
}
