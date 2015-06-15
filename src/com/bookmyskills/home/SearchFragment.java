package com.bookmyskills.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookmyskills.R;

@SuppressLint("NewApi")
public class SearchFragment extends android.support.v4.app.Fragment {

	public SearchFragment() {
		// Thread.setDefaultUncaughtExceptionHandler(new
		// UnCaughtException(getActivity()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);

		return rootView;
	}
}
