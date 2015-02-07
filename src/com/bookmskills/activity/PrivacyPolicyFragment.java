package com.bookmskills.activity;

import com.bookmyskills.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class PrivacyPolicyFragment extends Fragment {

	public PrivacyPolicyFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_privacy_policy,
				container, false);

		return rootView;
	}
}
