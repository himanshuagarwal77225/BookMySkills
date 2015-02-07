package com.bookmskills.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookmyskills.R;

@SuppressLint("NewApi")
public class TermsConditionFragment extends Fragment {

	public TermsConditionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_terms_conditions,
				container, false);

		return rootView;
	}
}
