package com.bookmskills.activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookmyskills.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

@SuppressLint("NewApi")
public class ContactUsFragment extends Fragment {

	public ContactUsFragment() {
	}

	ShimmerTextView tv;
	Shimmer shimmer;
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_contact_us,
				container, false);
		
		tv = (ShimmerTextView) rootView.findViewById(R.id.shimmer_tv);

		shimmer = new Shimmer();

		shimmer.setDuration(500).setStartDelay(300)
				.setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
		
		shimmer.start(tv);

		return rootView;
	}
}
